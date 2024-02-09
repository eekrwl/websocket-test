### 요청주소
**로컬에서 실행**
ws://localhost:8080/idrop

**임시배포** 2024.02.09 7:00PM
<br>
ws://0.tcp.jp.ngrok.io:14753/idrop

- ngrok으로 프론트와 연결되는지 테스트 예정

### 매칭된 사람들

driver1 - parent1

driver2 - parent2

### parent는 연결 후 요청 한번 보내준다

- 로그인까지 다 구현된 후 이거 지울 수 있을 것 같음
- 지금은 임시로 보냄
    
    ```json
    {
    	"sender":"parent1",
    	"receiver":"driver1"
    }
    ```
    

### 이후 driver가 parent에게 요청을 보내면 된다

- 이 모양 그대로 일단 parent에게 전달된다
- 이것도 추후 수정 예정

```json
{
	"sender":"driver1",
	"receiver":"parent1",
	"longitude":3.141592,
	"latitude": 110.3333
}
```

### 회원가입/로그인 구현 이후 수정예정
