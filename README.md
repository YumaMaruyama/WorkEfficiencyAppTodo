# WorkEfficiencyAppTodo

## [アプリ概要]
社内のメンバーとのメール、オープンチャットスペースやタスク管理機能により仕事の効率を上げるアプリです。
社内から一人代表を選んで管理者として、アプリ全体の管理も可能です。
ユーザー登録を行うと利用できるようになります。
* アプリURL： http://133.167.92.108:8084/login

## [主な使用技術]
##### 「プログラミング」
* Java8
* HTML5
* CSS3
* spring boot 2.4.2
* thymeleaf 3.0.12
* bootstrap 3.3.7-1
* jQuery 3.5.1
##### 「作成環境」
* Eclipse 4.20.0
* MySql 8.0.25
* GitHub 2.27.0
##### 「機能」
* tomcat 9.0.41
* apache 4.0.0
* springsecurity5
* lombok 1.18.16
* Devtools 2.4.2
* JDBC 5.3.3
* web 5.3.3
* font-awesome 4.7.0


## [設計書類]
 
 
### 1.[DB設計書(MySql)]  
アプリのDB情報が確認できます。

* #### DB設計書(Excel): https://github.com/YumaMaruyama/SpringBoot_Todo/files/6643903/spring_todo.DB.xlsx
 
 
 
 ## [アプリ説明] ##
  
実際にユーザー登録をして、機能を堪能することが可能です。  
* アプリURL：http://133.167.92.108:8084/login

1.URLにアクセスするとログイン前画面が表示されます。サイドバーの新規ユーザー登録からユーザー登録をしてから、登録したIDとパスワードでログインができます。
![スクリーンショット (18)-crop](https://user-images.githubusercontent.com/83486993/136146014-bdaea9cd-afc9-4b59-a158-5e0d920f1af2.png)


2.ログインすると商品一覧画面が表示され、さまざまな機能が利用可能になります。
![スクリーンショット (20)-crop](https://user-images.githubusercontent.com/83486993/136146117-efd14d08-eb46-46e9-9aa5-979095037b5d.png)



3.管理者専用画面は管理者専用IDとパスワードで表示されます。管理者のみが可能な機能や、閲覧可能な情報があります。
![スクリーンショット (23)-crop](https://user-images.githubusercontent.com/83486993/136148459-a28f1cbe-0cfd-427c-84b3-d606d412ce99.png)

3.1 ユーザー管理機能
　登録ユーザー全員の情報編集や退会させることが可能です。

3.2 連絡・おしらせ機能
　ユーザー個人や全ユーザーに管理者からがメッセージを送れます。送ったメッセージの削除も可能です。

3.3 お問い合わせ機能
　ログイン前とログイン後のユーザーからの要望などを早く把握できます。完了機能や削除が可能です。


