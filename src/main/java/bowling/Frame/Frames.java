package bowling.Frame;

import bowling.pin.Pins;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Frames {

    private static final int MIN_FRAME_INDEX = 1;
    private static final int MAX_FRAME_INDEX = 10;

    private List<Frame> frames;

    public Frames(){
        frames = new ArrayList<>();
        frames.add(new NormalFrame(MIN_FRAME_INDEX));
    }

    public void pitch(Pins pins){
        Frame nextFrame = getCurFrame().pitch(pins);

        if(nextFrame != getCurFrame()){
            frames.add(nextFrame);
        }
    }

    public int size(){
        return frames.size();
    }

    public Frame getCurFrame(){
        return frames.get(size()-1);
    }

    public List<Frame> getFrames() {
        return Collections.unmodifiableList(frames);
    }

}