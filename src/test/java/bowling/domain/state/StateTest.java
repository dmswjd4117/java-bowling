package bowling.domain.state;

import bowling.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class StateTest {

    @Test
    void 시작_상태를_생성한다() {
        //given
        State state = new Start();
        //when
        //then
        assertThat(state).isNotNull();
    }

    @Test
    void 일반_프레임_첫_번째_투구가_Strike_이면_상태는_End_이다() {
        //given
        Frame frame = NormalFrame.first();
        assertThat(frame.state()).isInstanceOf(Start.class);
        //when
        frame.state().run(Pitch.first(10), frame);
        //then
        assertThat(frame.state()).isInstanceOf(End.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"10,1", "10,0"})
    void 일반_프레임_첫_번째_투구가_Miss_혹은_Gutter_이면_프레임_상태는_Progress_retry_false_이다(int existPinsCount, int fallDownPinsCount) {
        //given
        Frame frame = NormalFrame.first();
        assertThat(frame.state()).isInstanceOf(Start.class);
        //when
        frame.state().run(Pitch.init(existPinsCount, fallDownPinsCount), frame);
        //then
        assertThat(frame.state()).isInstanceOf(Progress.class);
        assertThat(frame.state()).isEqualTo(new Progress(false));
    }

    @ParameterizedTest
    @CsvSource(value = {"10,2,8,2", "10,0,10,0", "10,5,5,5"})
    void 일반_프레임_첫_번째_두_번째_투구가_Miss_Gutter_Spare_이면_프레임_상태는_End_이다(
            int existPinsCount, int fallDownPinsCount, int secondExistPinsCount, int secondFallDownPinsCount) {
        //given
        Frame frame = NormalFrame.first();
        assertThat(frame.state()).isInstanceOf(Start.class);
        //when
        //first pitch
        frame.state().run(Pitch.init(existPinsCount, fallDownPinsCount), frame);
        assertThat(frame.state()).isInstanceOf(Progress.class);
        assertThat(frame.state()).isEqualTo(new Progress(false));

        //second pitch
        frame.state().run(Pitch.init(secondExistPinsCount, secondFallDownPinsCount), frame);
        //then
        assertThat(frame.state()).isInstanceOf(End.class);
    }

    @ParameterizedTest
    @CsvSource(value = {"10,1", "10,0"})
    void 마지막_프레임_첫_번째_투구가_Miss_혹은_Gutter_면_프레임_상태는_Progress_retry_false_이다(
            int existPinsCount, int fallDownPinsCount) {
        //given
        Frame frame = FinalFrame.create();
        assertThat(frame.state()).isInstanceOf(Start.class);
        //when
        frame.state().run(Pitch.init(existPinsCount, fallDownPinsCount), frame);
        //then
        assertThat(frame.state()).isInstanceOf(Progress.class);
        assertThat(frame.state()).isEqualTo(new Progress(false));
    }

    @Test
    void 마지막_프레임_첫_번째_투구가_스트라이크면_프레임_상태는_Progress_retry_true_이다() {
        //given
        Frame frame = FinalFrame.create();
        assertThat(frame.state()).isInstanceOf(Start.class);
        //when
        frame.state().run(Pitch.init(10, 10), frame);
        //then
        assertThat(frame.state()).isInstanceOf(Progress.class);
        assertThat(frame.state()).isEqualTo(new Progress(true));
    }

    @Test
    void 마지막_프레임_첫_번째_두_번째_투구가_스트라이크면_이후_프레임_상태는_Progress_retry_false_이다() {
        //given
        Frame frame = FinalFrame.create();
        assertThat(frame.state()).isInstanceOf(Start.class);
        //when
        //first pitch
        frame.state().run(Pitch.init(10, 10), frame);
        assertThat(frame.state()).isEqualTo(new Progress(true));

        //second pitch
        frame.state().run(Pitch.init(10, 10), frame);
        //then
        assertThat(frame.state()).isInstanceOf(Progress.class);
        assertThat(frame.state()).isEqualTo(new Progress(false));
    }

    @Test
    void 마지막_프레임_두_번째_투구가_Spare_면_이후_프레임_상태는_Progress_retry_false_이다() {
        //given
        Frame frame = FinalFrame.create();
        assertThat(frame.state()).isInstanceOf(Start.class);
        //when
        //first pitch
        frame.state().run(Pitch.init(10, 5), frame);
        assertThat(frame.state()).isEqualTo(new Progress(false));

        //second pitch
        frame.state().run(Pitch.init(5, 5), frame);
        //then
        assertThat(frame.state()).isInstanceOf(Progress.class);
        assertThat(frame.state()).isEqualTo(new Progress(false));
    }

    @Test
    void 마지막_프레임_Gutter_Spare_면_이후_프레임_상태는_Progress_retry_false_이다() {
        //given
        Frame frame = FinalFrame.create();
        assertThat(frame.state()).isInstanceOf(Start.class);
        //when
        //first pitch
        frame.state().run(Pitch.init(10, 0), frame);
        assertThat(frame.state()).isEqualTo(new Progress(false));

        //second pitch
        frame.state().run(Pitch.init(10, 10), frame);
        //then
        assertThat(frame.state()).isInstanceOf(Progress.class);
        assertThat(frame.state()).isEqualTo(new Progress(false));
    }

    @Test
    void 마지막_프레임_Strike_Strike_Strike_면_이후_프레임_상태는_End_이다() {
        //given
        Frame frame = FinalFrame.create();
        assertThat(frame.state()).isInstanceOf(Start.class);
        //when
        //first pitch
        frame.state().run(Pitch.init(10, 10), frame);
        assertThat(frame.state()).isEqualTo(new Progress(true));

        //second pitch
        frame.state().run(Pitch.init(10, 10), frame);
        assertThat(frame.state()).isEqualTo(new Progress(false));

        //third pitch
        frame.state().run(Pitch.init(10, 10), frame);
        //then
        assertThat(frame.state()).isInstanceOf(End.class);
    }

    @Test
    void 종료_상태에서_투구하면_IllegalArgumentException_이_발생한다() {
        //given
        Frame frame = FinalFrame.create();
        frame.state().run(Pitch.init(10, 5), frame);
        assertThat(frame.state()).isEqualTo(new Progress(false));
        frame.state().run(Pitch.init(5, 3), frame);
        assertThat(frame.state()).isInstanceOf(End.class);

        //when & then
        assertThatThrownBy(() -> frame.state().run(Pitch.init(2, 2), frame))
                .isInstanceOf(IllegalArgumentException.class);
    }
}