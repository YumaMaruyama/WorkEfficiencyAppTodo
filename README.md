# WorkEfficiencyAppTodo 
![new](https://user-images.githubusercontent.com/83486993/149530455-f3d6a997-8d94-49bb-bfb1-eadb9052a288.png)



## [アプリ概要]
社内のメンバーとのメール、オープンチャットスペースやタスク管理機能により仕事の効率を上げるアプリです。  
社内から一人代表を選んで管理者として、アプリ全体の管理も可能です。  
ユーザー登録を行うと利用できるようになります。
* アプリURL： http://133.167.92.108:8084/login

## [使用技術]
* Java8
* HTML5
* CSS3
* spring boot 2.4.2
* thymeleaf 3.0.4
* bootstrap 4.2.1
* jQuery 3.5.1

## [動作環境]
* MariaDB 10.3.28
* apache 2.4.37 

## [設計書類]

DB設計書(Excel):[spring_todo DB詳細.xlsx](https://github.com/YumaMaruyama/WorkEfficiencyAppTodo/files/7424722/spring_todo.DB.xlsx)


 
 ## [アプリ説明] ##
  
実際にユーザー登録をして、機能を堪能することが可能です。  
* アプリURL：http://133.167.92.108:8084/login

1.URLにアクセスするとログイン前画面が表示されます。サイドバーの新規ユーザー登録からユーザー登録をしてから、登録したIDとパスワードでログインができます。
![スクリーンショット (18)-crop](https://user-images.githubusercontent.com/83486993/136146014-bdaea9cd-afc9-4b59-a158-5e0d920f1af2.png)


2.ログインすると商品一覧画面が表示され、さまざまな機能が利用可能になります。
![スクリーンショット (20)-crop](https://user-images.githubusercontent.com/83486993/136146117-efd14d08-eb46-46e9-9aa5-979095037b5d.png)



3.管理者用IDとパスワードでログインすると管理者専用画面が表示されます。通常のログイン画面に、管理者のみが閲覧、登録可能な機能を付与した画面になります。
![スクリーンショット (23)-crop](https://user-images.githubusercontent.com/83486993/136148459-a28f1cbe-0cfd-427c-84b3-d606d412ce99.png)

3.1 ユーザー管理機能
　登録ユーザー全員の情報編集や退会させることが可能です。

3.2 連絡・おしらせ機能
　ユーザー個人や全ユーザーに管理者からがメッセージを送れます。送ったメッセージの削除も可能です。

3.3 お問い合わせ機能
　ログイン前とログイン後のユーザーからの要望などを早く把握できます。完了機能や削除が可能です。


