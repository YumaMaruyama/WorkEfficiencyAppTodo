package com.example.demo.login.domain.model;

import java.util.Date;

import lombok.Data;

@Data
public class TweetDTO {

	private int id;

	private String contents;

	private Date registration_date;

	private String user_id;

	private String user_id2;//ツイートを削除するとき自身のツイートしか削除できないようにするためのカラム
}
