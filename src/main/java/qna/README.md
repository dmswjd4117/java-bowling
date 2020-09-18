# 1단계 - 질문 삭제하기 기능 리팩토링
## 진행 방법
* 볼링 게임 점수판 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 질문 삭제하기 요구사항
* 질문 데이터를 완전히 삭제하는 것이 아니라 데이터의 상태를 삭제 상태(deleted - boolean type)로 변경한다.
* 로그인 사용자와 질문한 사람이 같은 경우 삭제 가능하다.
* 답변이 없는 경우 삭제가 가능하다.
* 질문자와 답변글의 모든 답변자 같은 경우 삭제가 가능하다.
* 질문을 삭제할 때 답변 또한 삭제해야 하며, 답변의 삭제 또한 삭제 상태(deleted)를 변경한다.
* 질문자와 답변자가 다른 경우 답변을 삭제할 수 없다.
* 질문과 답변 삭제 이력에 대한 정보를 DeleteHistory를 활용해 남긴다.

## 프로그래밍 요구사항
* qna.service.QnaService의 deleteQuestion()는 앞의 질문 삭제 기능을 구현한 코드이다. 이 메소드는 단위 테스트하기 어려운 코드와 단위 테스트 가능한 코드가 섞여 있다.
* 단위 테스트하기 어려운 코드와 단위 테스트 가능한 코드를 분리해 단위 테스트 가능한 코드 에 대해 단위 테스트를 구현한다.


## 리팩토링 리스트
refactor 1. Question 객체에서 담당할 책임 분리하기
            * valiateOwner Question으로 옮기기
            * deleteQuestion Question으로 옮기기
refactor 2. List<DeleteHistory>의 일급컬렉션 만들기
            * refactor 1에서 만든 deleteQuestion test에서 사용하는 question 객체 before each에서 만들기 (테스트끼리 영향을 끼치지 않기위해)
refactor 3. Answer 객체에서 담당할 책임 분리하기
            * answer Owner 확인 로직 Answer로 옮기기
            * deleteAnswer Answer로 옮기기
refactor 4. List<Answer>의 일급컬렉션 만들기

refactor 5. 코드리뷰 반영
            * 파라미터로 전달받은 컬렉션을 조작하는 로직 제거
            * DeleteHistories의 멤버 변수 컬렉션은 unmodifable로 변경 