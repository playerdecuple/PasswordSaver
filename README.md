# Password Saver
> 비밀번호 잊어버릴 때마다 '비밀번호 찾기' 기능을 쓰기 귀찮으신 분들에게 적합한 프로그램!

## Is it virus?
이 프로그램은 바이러스가 아니며, JAR 파일 및 java 파일에 악성코드 또는 개인정보를 탈취하는 스파이웨어 코드가 없음을 알려드립니다.  
불안하신 분들은 직접 빌드해서 사용해 주세요. (코드를 꼼꼼히 읽으신 후..)

## Installation
1. [릴리즈 페이지](https://github.com/playerdecuple/PasswordSaver/releases/)로 이동합니다.
2. `PasswordSaver-v[버전]-Release-Windows.zip`을 다운로드 받습니다.
3. 압축을 해제하고, `run.bat`을 실행시킵니다.
4. 16자리 이하의 패스워드를 생성합니다.
5. 프로그램을 재시작하고 안내에 따릅니다.

## How it works?
1. 사용자가 비밀번호를 생성할 경우 `SHA-256`으로 암호화하여 `Configuration` 디렉토리 내에 `Password.decuple`이라는 파일로 저장합니다.
2. 다음에 사용자가 비밀번호를 입력할 경우 입력한 비밀번호를 `SHA-256`으로 암호화한 다음, `Password.decuple`의 내용과 대조합니다.
3. 비밀번호가 일치할 경우 데이터베이스를 보여줍니다.
4. 만약 데이터베이스에 새로운 아이디 및 비밀번호를 추가할 경우 사용자의 `Password`를 이용하여 추가할 ID와 비밀번호를 `AES-128`로 암호화합니다.
5. 암호화한 ID 및 비밀번호를 데이터베이스 디렉토리 내의 `ID.decuple`, `PW.decuple`로 저장합니다.
6. 다음에 사용자가 해당 데이터베이스를 확인할 경우 `ID.decuple`, `PW.decuple`의 정보를 읽어와 복호화하고 사용자에게 보여줍니다.

## PatchNotes
### v1.0.2 (Released)
* ID와 비밀번호 저장할 때 나오는 설명 변경
* 여러 설명 및 UI 변경
* Configuration의 `Password.decuple` 삭제 시 Database 초기화

### v1.0.1
* 새롭게 ID와 비밀번호 저장 기능 추가
* 설명 및 UI 변경

### v1.0.0
* ID, 비밀번호 초기 저장 기능 및 확인 기능 추가
