package com.example.demo.login.domain.model;

import javax.validation.GroupSequence;

//バリデーションをグループ実行するには、グループの実行順序用のインターフェースを用意する
//そのインターフェースには@GroupSequenceをつける　
//アノテーションのパラメーターに、各グループのclassを指定する
@GroupSequence({ValidGroup1.class,ValidGroup2.class,ValidGroup3.class})

public interface GroupOrder {

}
