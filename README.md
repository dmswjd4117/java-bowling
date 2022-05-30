# 볼링 게임 점수판
## 진행 방법
* 볼링 게임 점수판 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## 기능
---

- `Input`
    - 플레이어의 이름을 입력받는다.
    - 한 투구에서 쓰러트린 핀의 개수를 입력받는다.

- `Output`
    - 하나의 투구가 끝날때마다 결과를 출력한다.

- `Player`
    - 이름은 영어 3글자만 허용한다.

- `Pins`
    - 핀은 0~10개의 개수만 허용한다.

- `Frame`
    - 1~9 프레임은 NormalFrame, 10프레임은 FinalFrame이다.

    - `NormalFrame`
        - 최대 2개의 투구를 할 수 있는 프레임이다.
        - 1~8 프레임은 투구후 NormalFrame프레임을 반환한다.
        - 9프레임은 투구후 FinalFrame을 반환한다.

    - `FinalFrame`
        - 스트라이크 또는 스페어시 추가적으로 한 번더 투구할 수 있다. (총 3번을 투구할 수 있다.)

- `Frames`
    - 10개의 프레임을 관리하기 위한 일급컬렉션

- `Bowl`
    - Ready, First, 추상클래스 Ended 의 인터페이스다.
    - `Ended`
        - 진행이 끝난 bowl의 추상 클래스다.
        - Gutter, Miss, Spare, Strike는 Ended를 상속한다.
        - Gutter는 모든 핀을 맞추지 못한 상태다.
        - Spares는 두번째 투구때 모든 핀을 맞춘상태다.
        - Strike는 첫 번째 투구때 모든 핀을 맞춘상태다.
        - Miss는 두 번째 투구 후에도 맞추지 못한 핀이 있는 상태다.
        
    - `Running`
       - 진행이 끝난 bowl의 추상 클래스다.
       - Ready, First는 Running을 상속한다.
       - Ready는 투구를 던지기 위한 준비 상태다.
       - First는 첫 번째 투구를 던진후 상태다.
       
--- 


## 질문 삭제하기 요구사항

### 질문
- [x] 질문 데이터를 완전히 삭제하는 것이 아니라 데이터의 상태를 삭제 상태(deleted - boolean type)로 변경한다.
- [x] 로그인 사용자와 질문한 사람이 다른 경우 삭제할 수 없다.
- [x] 답변이 없는 경우 삭제 가능하다.
- [x] 답변이 있는 경우 답변자와 질문자가 같으면 삭제가능하다.

### 답변
- [x] 답변을 삭제할 때 삭제 상태를 변경하다.
- [x] 질문이 삭제될 때 답변도 같이 삭제가 된다.

### DeleteHistory
- [x] 질문과 답변 삭제 이력에 대한 정보를 남긴다.