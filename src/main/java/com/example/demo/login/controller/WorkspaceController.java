package com.example.demo.login.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.login.domain.model.AdminDTO;
import com.example.demo.login.domain.model.AdminForm;
import com.example.demo.login.domain.model.AdminNoticeSearchForm;
import com.example.demo.login.domain.model.AdminPersonNoticeDetailForm;
import com.example.demo.login.domain.model.AdminPersonNoticeSearchForm;
import com.example.demo.login.domain.model.ClientAddtoForm;
import com.example.demo.login.domain.model.ClientDTO;
import com.example.demo.login.domain.model.ClientDeleteForm;
import com.example.demo.login.domain.model.ClientDetailForm;
import com.example.demo.login.domain.model.ClientSearchForm;
import com.example.demo.login.domain.model.GroupOrder;
import com.example.demo.login.domain.model.GuideForm;
import com.example.demo.login.domain.model.InquiryDTO;
import com.example.demo.login.domain.model.InquiryForm;
import com.example.demo.login.domain.model.InquirySearchForm;
import com.example.demo.login.domain.model.One_to_oneMailDTO;
import com.example.demo.login.domain.model.One_to_oneMailDetailForm;
import com.example.demo.login.domain.model.One_to_oneMailForm;
import com.example.demo.login.domain.model.One_to_oneMailHomeSearchForm;
import com.example.demo.login.domain.model.One_to_oneMailInquiryForm;
import com.example.demo.login.domain.model.One_to_oneMailReplyForm;
import com.example.demo.login.domain.model.One_to_oneMailSearchForm;
import com.example.demo.login.domain.model.One_to_oneMailSendingDetailForm;
import com.example.demo.login.domain.model.One_to_oneMailSendingForm;
import com.example.demo.login.domain.model.PersonMemoDTO;
import com.example.demo.login.domain.model.PersonMemoForm;
import com.example.demo.login.domain.model.PersonMemoSearchForm;
import com.example.demo.login.domain.model.PersonUsersNoticeDTO;
import com.example.demo.login.domain.model.PersonUsersNoticeInquiryForm;
import com.example.demo.login.domain.model.PersonUsersNoticeSearchForm;
import com.example.demo.login.domain.model.PersonUsersNoticeSendingDetailForm;
import com.example.demo.login.domain.model.PersonUsersNoticeSendingForm;
import com.example.demo.login.domain.model.PersonUsersNoticeSendingSearchForm;
import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.SignupUpdateForm;
import com.example.demo.login.domain.model.Todo_itemsDTO;
import com.example.demo.login.domain.model.TweetDTO;
import com.example.demo.login.domain.model.TweetForm;
import com.example.demo.login.domain.model.TweetSearchForm;
import com.example.demo.login.domain.model.UsersDTO;
import com.example.demo.login.domain.model.UsersSearchForm;
import com.example.demo.login.domain.model.WorkaddtoDetailForm;
import com.example.demo.login.domain.model.WorkaddtoForm;
import com.example.demo.login.domain.model.WorkspaceSearchForm;
import com.example.demo.login.domain.service.AdminService;
import com.example.demo.login.domain.service.ClientService;
import com.example.demo.login.domain.service.InquiryService;
import com.example.demo.login.domain.service.One_to_oneMailService;
import com.example.demo.login.domain.service.PersonMemoService;
import com.example.demo.login.domain.service.PersonUsersNoticeService;
import com.example.demo.login.domain.service.Todo_itemsService;
import com.example.demo.login.domain.service.TweetService;
import com.example.demo.login.domain.service.UsersService;

@Controller
public class WorkspaceController {

	@Autowired
	private UsersService usersService;
	@Autowired
	private Todo_itemsService todo_itemsService;
	@Autowired
	private InquiryService inquiryService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private TweetService tweetService;
	@Autowired
	private PersonMemoService personMemoService;
	@Autowired
	private PersonUsersNoticeService personUsersNoticeService;
	@Autowired
	private One_to_oneMailService one_to_oneMailService;
	@Autowired
	private ClientService clientService;
	@Autowired //これを付けるとSessionが使用できる
	HttpSession session;

	//性別ステータスのラジオボタン用変数
	private Map<String, String> radioMaleFemale;

	//ラジオボタンの初期化用メソッド（ユーザー登録画面と同じ）
	private Map<String, String> initRadioMaleFemale() {

		Map<String, String> radio = new LinkedHashMap<>();

		//男・女をMapに格納
		radio.put("男", "true");
		radio.put("女", "false");

		return radio;
	}

	@GetMapping("/termsOfService")
	public String getTermsOfServise(Model model) {

		model.addAttribute("contents", "login/termsOfService::workspaceLayout_contents");

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return "login/workspaceLayout";
	}

	@GetMapping("/termsOfServiceLogin")
	public String getTermsOfServiceLogin(Model model) {
		model.addAttribute("contents", "login/termsOfServiceLogin::loginLayout_contents");
		return "login/loginLayout";
	}

	@GetMapping("/privacyPolicy")
	public String getPrivacyPolicy(Model model) {

		model.addAttribute("contents", "login/privacyPolicy::workspaceLayout_contents");

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return "login/workspaceLayout";
	}

	@GetMapping("/privacyPolicyLogin")
	public String getPrivacyPolisy(Model model) {
		model.addAttribute("contents", "login/privacyPolicyLogin::loginLayout_contents");
		return "login/loginLayout";
	}

	//workspace画面のGET用メソッド
	@GetMapping("/workspace")
	public String getWorkspace(Model model, @ModelAttribute WorkspaceSearchForm form) {

		System.out.println("getWorkspace到達");
		//コンテンツ部分にユーザ一覧を表示するための文字列
		model.addAttribute("contents", "login/workspace::workspaceLayout_contents");

		//ログイン者のuser_id(メールアドレス)をもとに、user_name（丸山佑馬）を取得する
		//springsecrityの機能でuser_idがuser_nameで持ってこれる
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth.getName());

		//@AutowiredがついてるのでUsersServiceのインスタンス（usersService）を使う
		UsersDTO headerName = usersService.selectTwo(auth.getName());
		System.out.println("headerName" + headerName);

		session.setAttribute("sessionGetUser_name", headerName.getUser_name());
		System.out.println("sessionGetUser_name" + headerName.getUser_name());
		List<Todo_itemsDTO> todo_itemsList = todo_itemsService.selectMany();

		model.addAttribute("todo_itemsList", todo_itemsList);

		int count = todo_itemsService.count();
		model.addAttribute("workspaceHomeCount", count);

		int count2 = todo_itemsService.count();
		model.addAttribute("workspaceCount", count2);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());

		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return "login/workspaceLayout";
	}

	@GetMapping("/workspace/csv")
	public ResponseEntity<byte[]> getWorkspaceCsv(Model model) {

		todo_itemsService.todo_itemsCsvOut();

		byte[] bytes = null;

		try {

			bytes = todo_itemsService.getFile("workspace.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}

		HttpHeaders header = new HttpHeaders();
		header.setContentDispositionFormData("filename", "WorkList.csv");

		return new ResponseEntity<>(bytes, header, HttpStatus.OK);

	}

	//workspace画面のGET用メソッド
	@PostMapping("/workspace")
	public String postWorkspace(@ModelAttribute WorkaddtoForm form, Model model) {

		//ユーザー一覧の生成
		List<Todo_itemsDTO> todo_itemsList = todo_itemsService.selectMany();

		//Modekにユーザーリストを登録
		model.addAttribute("todo_itemsList", todo_itemsList);

		return "login/workspace";

	}

	@PostMapping(value = "/workspace", params = "search")
	public String postWorkspace(@ModelAttribute @Validated WorkspaceSearchForm form, BindingResult bindingResult,
			@RequestParam("search") String todo_itemsdto, Model model) throws ParseException {

		//データバインドに失敗（入力チェックに引っかかる）の場合はworkspaceに戻る
		if (bindingResult.hasErrors()) {
			System.out.println("workspaceSearchエラーチェック");
			//GETリクエスト用のメソッドを呼び出して、workspaceに戻る
			return getWorkspace(model, form);
		}

		System.out.println("workSearchController到達");

		System.out.println("getExpire_dateA" + form.getExpire_dateA()); //登録日(始まり)
		System.out.println("getExpire_dateZ" + form.getExpire_dateZ());//登録日(終わり)
		System.out.println("getRegistration_dateFrom" + form.getRegistration_dateA());//期限日(始まり)
		System.out.println("getRegistration_dateTO" + form.getRegistration_dateZ());//期限日(終わり)
		System.out.println("getFinished_dateFrom" + form.getFinished_dateA());//完了日(始まり)
		System.out.println("getFinished_dateTo" + form.getFinished_dateZ());//完了日(終わり)
		System.out.println("getFinished_dateCheck" + form.getFinished_dateM());//未完了に絞る

		String Finished_dateM = null;

		if ("true".equals(form.getFinished_dateM())) {

			String dateStr = form.getFinished_dateM();

			Finished_dateM = dateStr;
			System.out.println("Finished_dateM" + Finished_dateM);
		}

		model.addAttribute("contents", "login/workspace::workspaceLayout_contents");

		
		List<Todo_itemsDTO> todo_itemsList = todo_itemsService.search(form.getItem_name(), form.getUser_name(),
				form.getRegistration_dateA(), form.getRegistration_dateZ(), form.getExpire_dateA(),
				form.getExpire_dateZ(), form.getFinished_dateA(),
				form.getFinished_dateZ(), Finished_dateM);
		System.out.println("todo_itemsList" + todo_itemsList);

		model.addAttribute("todo_itemsList", todo_itemsList);

		model.addAttribute("workspaceHomeCount", todo_itemsList.size());

		int count2 = todo_itemsService.count();
		model.addAttribute("workspaceCount", count2);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return "/login/workspaceLayout";
	}

	@GetMapping("/one_to_oneMail")
	public String getOne_to_oneMail(@ModelAttribute One_to_oneMailForm form, One_to_oneMailHomeSearchForm form1,
			One_to_oneMailDetailForm form2, Model model) {
		model.addAttribute("contents", "login/one_to_oneMail::workspaceLayout_contents");
		System.out.println("getOne_to_oneMail到達");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth);
		System.out.println("auth.getName" + auth.getName());

		String getName = auth.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailHomeCount", one_to_onemailList.size());
		model.addAttribute("one_to_onemailList", one_to_onemailList);

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		List<One_to_oneMailDTO> one_to_onemailCount = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailCount.size());

		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return "/login/workspaceLayout";
	}

	@PostMapping("/one_to_oneMail")
	public String postOne_to_oneMail(@ModelAttribute One_to_oneMailForm form, One_to_oneMailHomeSearchForm form1,
			Model model, List<One_to_oneMailDTO> one_to_onemailList) {
		model.addAttribute("contents", "login/one_to_oneMail::workspaceLayout_contents");
		System.out.println("postOne_to_oneMail到達");
		model.addAttribute("one_to_onemailList", one_to_onemailList);

		return "/login/workspaceLayout";

	}

	@GetMapping("/one_to_oneMailDetail/{id}")
	public String getOne_to_oneMailDetail(@ModelAttribute One_to_oneMailDetailForm form, Model model,
			@PathVariable("id") String id) {
		model.addAttribute("contents", "login/one_to_oneMailDetail::workspaceLayout_contents");
		System.out.println("one_to_oneMailDetail到達");
		One_to_oneMailDTO getOne_to_onemail = one_to_oneMailService.selectOne(id);

		model.addAttribute("one_to_onemailId", getOne_to_onemail.getId());
		model.addAttribute("one_to_onemailUser_name", getOne_to_onemail.getUser_name());
		model.addAttribute("one_to_onemailMail", getOne_to_onemail.getMail());
		model.addAttribute("one_to_onemailRegistration_date", getOne_to_onemail.getRegistration_date());

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return "login/workspaceLayout";

	}

	@PostMapping(value = "/one_to_oneMailDetail", params = "delete")
	public String postOne_to_oneMailDetailDelete(@ModelAttribute One_to_oneMailDetailForm form, Model model) {
		model.addAttribute("contents", "login/one_to_oneMail::workspaceLayout_contents");
		System.out.println("one_tooneMailDetaiDelete到達");

		int rowNumber = one_to_oneMailService.deleteOne(form.getId());

		if (rowNumber > 0) {
			System.out.println("delete成功");
		} else {
			System.out.println("delete失敗");
		}
		One_to_oneMailForm one_to_onemailform = new One_to_oneMailForm();
		model.addAttribute("one_to_oneMailForm", one_to_onemailform);
		One_to_oneMailHomeSearchForm one_to_oneMailHomeSearchForm = new One_to_oneMailHomeSearchForm();
		model.addAttribute("one_to_oneMailHomeSearchForm", one_to_oneMailHomeSearchForm);
		return getOne_to_oneMail(one_to_onemailform, one_to_oneMailHomeSearchForm, form, model);
	}

	@PostMapping(value = "/one_to_oneMail", params = "search")
	public String postOne_to_oneMailSearch(@ModelAttribute @Validated One_to_oneMailHomeSearchForm form,
			BindingResult bindingResult, Model model) {
		model.addAttribute("contents", "login/one_to_oneMail::workspaceLayout_contents");

		System.out.println("postOne_to_oneMailSearch到達");
		System.out.println("getForm.user_name  " + form.getUser_name());
		if (bindingResult.hasErrors()) {
			System.out.println("エラーチェック到達");
			One_to_oneMailForm one_to_onemailform = new One_to_oneMailForm();
			model.addAttribute("One_to_oneMailForm", one_to_onemailform);

			One_to_oneMailHomeSearchForm one_to_onemailhomesearchform = new One_to_oneMailHomeSearchForm();
			model.addAttribute("one_to_onemailhomesearchform", one_to_onemailhomesearchform);

			One_to_oneMailDetailForm one_to_onemaildetailform = new One_to_oneMailDetailForm();
			model.addAttribute("one_to_onemaildetailform", one_to_onemaildetailform);

			int count = todo_itemsService.count();
			model.addAttribute("workspaceCount", count);

			int countTweet = tweetService.count();
			model.addAttribute("tweetcount", countTweet);

			int countAdminNotice = adminService.count();
			model.addAttribute("adminListCount", countAdminNotice);

			return getOne_to_oneMail(one_to_onemailform, one_to_onemailhomesearchform, one_to_onemaildetailform, model);
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth);
		System.out.println("auth.getName" + auth.getName());

		String loginUser_id = auth.getName();
		String loginUser_id2 = auth.getName();

		List<One_to_oneMailDTO> searchList = one_to_oneMailService.search(form.getUser_name(), form.getMail(),
				form.getRegistration_dateFrom(), form.getRegistration_dateTo(), loginUser_id, loginUser_id2);

		System.out.println("searchList  " + searchList);

		model.addAttribute("searchList", searchList);

		model.addAttribute("one_to_oneMailHomeCount", searchList.size());

		One_to_oneMailForm one_to_oneMailForm = new One_to_oneMailForm();
		model.addAttribute("one_to_oneMailForm", one_to_oneMailForm);

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());

		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return postOne_to_oneMail(one_to_oneMailForm, form, model, searchList);

	}

	@GetMapping("/one_to_oneMailReply/{sender}/{id}")
	public String getOne_to_oneMailReply(@ModelAttribute One_to_oneMailReplyForm form, Model model,
			@PathVariable("sender") String sender, @PathVariable("id") int id) {
		model.addAttribute("contents", "login/one_to_oneMailReply::workspaceLayout_contents");
		model.addAttribute("hiddenSender", sender);

		One_to_oneMailDTO one_to_oneMailList = one_to_oneMailService.selectOneReply(id);
		System.out.println("one_to_oneMailList" + one_to_oneMailList);
		model.addAttribute("getUser_name", one_to_oneMailList.getUser_name());
		model.addAttribute("getMail", one_to_oneMailList.getMail());
		model.addAttribute("getRegistration_date", one_to_oneMailList.getRegistration_date());

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return "login/workspacelayout";
	}

	@PostMapping("/one_to_oneMailReply/{id}")
	public String postOne_to_oneMailReply(@ModelAttribute @Validated One_to_oneMailReplyForm form,
			BindingResult bindingResult, Model model, @PathVariable("id") int id) {
		model.addAttribute("contents", "login/one_to_oneMailReply::workspaceLayout_contents");
		System.out.println("id    " + id);
		if (bindingResult.hasErrors()) {
			String sender = form.getSender();
			int Id = id;
			return getOne_to_oneMailReply(form, model, sender, Id);
		}

		System.out.println("form.getSender" + form.getSender());
		System.out.println("form,getMail" + form.getMail());

		One_to_oneMailDTO one_to_onemaildto = new One_to_oneMailDTO();
		one_to_onemaildto.setSender(form.getSender());
		one_to_onemaildto.setMail(form.getMail());

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth);
		System.out.println("auth.getName" + auth.getName());

		String getName = auth.getName();
		boolean result = one_to_oneMailService.insertOneReply(one_to_onemaildto, getName);

		if (result == true) {
			System.out.println("insert成功");
		} else {
			System.out.println("insert失敗");
		}

		model.addAttribute("result", "返信完了");
		String getSender = String.valueOf(form.getSender());
		int Id = id;
		System.out.println("id    " + Id);
		return getOne_to_oneMailReply(form, model, getSender, Id);

	}

	@GetMapping("/one_to_oneMailNotice")
	public String getOne_to_oneMailNotice(@ModelAttribute One_to_oneMailSearchForm form, Model model) {
		model.addAttribute("contents", "login/one_to_oneMailNotice::workspaceLayout_contents");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth);
		System.out.println("auth.getName" + auth.getName());

		String getName = auth.getName();

		List<UsersDTO> usersList = usersService.selectManyOne_to_oneMail(getName);
		System.out.println("usersList" + usersList);
		model.addAttribute("usersList", usersList);
		model.addAttribute("one_to_oneMailSearchCount", usersList.size());

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return "/login/workspaceLayout";
	}

	@PostMapping("/one_to_oneMailNotice")
	public String postOne_to_oneMailNotice(@ModelAttribute One_to_oneMailSearchForm form, Model model,
			List<UsersDTO> usersList) {
		model.addAttribute("contents", "login/one_to_oneMailNotice::workspaceLayout_contents");

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		model.addAttribute("usersList", usersList);
		return "/login/workspaceLayout";
	}

	@PostMapping(value = "/one_to_oneMailNotice", params = "search")
	public String postOne_to_oneMailNoticeSearch(@ModelAttribute @Validated One_to_oneMailSearchForm form,
			BindingResult bindingResult, Model model) {
		model.addAttribute("contents", "login/one_to_oneMailNotice::workspaceLayout_contents");

		if (bindingResult.hasErrors()) {
			System.out.println("エラーチェック到達");
			getOne_to_oneMailNotice(form, model);
		}

		String admin = "ROLE_ADMIN";
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth);
		System.out.println("auth.getName" + auth.getName());

		String getName = auth.getName();
		List<UsersDTO> getSearchList = usersService.searchOne_to_oneMailNotice(form.getUser_id(), form.getUser_name(),
				admin, getName);

		System.out.println("getSearchList  " + getSearchList);
		model.addAttribute("getSearchList", getSearchList);
		model.addAttribute("one_to_oneMailSearchCount", getSearchList.size());

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return postOne_to_oneMailNotice(form, model, getSearchList);

	}

	@GetMapping("/one_to_oneMailNoticeDetail/{user_id}")
	public String getOne_to_oneMailNoticeDetail(@ModelAttribute One_to_oneMailInquiryForm form, Model model,
			@PathVariable("user_id") String user_id) {
		System.out.println("/one_to_oneMailNoticeDetail/{user_id}到達");
		model.addAttribute("contents", "login/one_to_oneMailNoticeDetail::workspaceLayout_contents");
		model.addAttribute("hiddenUser_id", user_id);

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return "/login/workspaceLayout";
	}

	@PostMapping("/one_to_oneMailNoticeDetail")
	public String postOne_to_oneMailNoticeDetail(@ModelAttribute @Validated One_to_oneMailInquiryForm form,
			BindingResult bindingResult, Model model) {
		model.addAttribute("contents", "login/one_to_oneMailNoticeDetail::workspaceLayout_contents");
		System.out.println("postOne_to_oneMailNoticeDetail到達");

		if (bindingResult.hasErrors()) {
			String getUser_id = form.getUser_id();

			return getOne_to_oneMailNoticeDetail(form, model, getUser_id);
		}

		System.out.println("form.getUser_id" + form.getUser_id());
		System.out.println("form.getMail" + form.getMail());

		One_to_oneMailDTO one_to_onemaildto = new One_to_oneMailDTO();

		one_to_onemaildto.setUser_id(form.getUser_id());
		one_to_onemaildto.setMail(form.getMail());

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth);
		System.out.println("auth.getName" + auth.getName());

		String getName = auth.getName();

		boolean result = one_to_oneMailService.insertOne(one_to_onemaildto, getName);

		System.out.println("result" + result);

		model.addAttribute("result", "送信完了");

		String getUser_id = String.valueOf(form.getUser_id());
		return getOne_to_oneMailNoticeDetail(form, model, getUser_id);

	}

	@GetMapping("/one_to_oneMailSending")
	public String getOne_to_oneMailSending(@ModelAttribute One_to_oneMailSendingForm form,
			One_to_oneMailSendingDetailForm form2, Model model) {
		model.addAttribute("contents", "login/one_to_oneMailSending::workspaceLayout_contents");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth);
		System.out.println("auth.getName" + auth.getName());

		String getName = auth.getName();

		int one_to_onemailCountList = one_to_oneMailService.countSending(getName);
		model.addAttribute("one_to_oneMailHomeCount", one_to_onemailCountList);

		List<One_to_oneMailDTO> one_to_oneMailList = one_to_oneMailService.selectManySending(getName);

		System.out.println("one_to_oneMailList  " + one_to_oneMailList);

		model.addAttribute("one_to_oneMailList", one_to_oneMailList);

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());

		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		return "login/workspaceLayout";

	}

	@PostMapping(value = "/one_to_oneMailSending", params = "search")
	public String postOne_to_oneMaileSendingSearch(@ModelAttribute @Validated One_to_oneMailSendingForm form,
			BindingResult bindingResult, Model model) {
		model.addAttribute("contents", "login/one_to_oneMailSending::workspaceLayout_contents");

		if (bindingResult.hasErrors()) {
			System.out.println("エラーチェック到達");
			One_to_oneMailSendingDetailForm one_to_onemailsendingDetailform = new One_to_oneMailSendingDetailForm();
			model.addAttribute("one_to_onemailsendingDetailform", one_to_onemailsendingDetailform);

			return getOne_to_oneMailSending(form, one_to_onemailsendingDetailform, model);
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth);
		System.out.println("auth.getName" + auth.getName());

		String getName = auth.getName();

		List<One_to_oneMailDTO> one_to_oneMailList = one_to_oneMailService.searchSending(form.getUser_name(),
				form.getMail(), form.getRegistration_dateFrom(), form.getRegistration_dateTo(), getName);

		System.out.println("one_to_oneMailList  " + one_to_oneMailList);

		model.addAttribute("one_to_oneMailList", one_to_oneMailList);
		model.addAttribute("one_to_oneMailHomeCount", one_to_oneMailList.size());

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());

		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		return "login/workspaceLayout";

	}

	@GetMapping("/one_to_oneMailSendingDetail/{id}")
	public String getOne_to_oneMailSendingDetail(@ModelAttribute One_to_oneMailSendingDetailForm form, Model model,
			@PathVariable("id") int id) {
		model.addAttribute("contents", "login/one_to_oneMailSendingDetail::workspaceLayout_contents");

		System.out.println("one_to_oneMailSendingDetail到達");
		One_to_oneMailDTO one_to_onemaildto = one_to_oneMailService.selectOneSendingDetail(id);

		System.out.println("one_to_onemaildto  " + one_to_onemaildto);
		System.out.println(one_to_onemaildto.getUser_name());
		model.addAttribute("getUser_name", one_to_onemaildto.getUser_name());
		model.addAttribute("getMail", one_to_onemaildto.getMail());
		model.addAttribute("getRegistration_date", one_to_onemaildto.getRegistration_date());
		model.addAttribute("id", id);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth);
		System.out.println("auth.getName" + auth.getName());

		String getName = auth.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());

		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		return "login/workspaceLayout";
	}

	@PostMapping(value = "/one_to_oneMailSendingDetail", params = "delete")
	public String postOne_to_oneMailSendingDetailDelete(@ModelAttribute One_to_oneMailSendingDetailForm form,
			One_to_oneMailSendingForm form2, Model model) {
		model.addAttribute("contents", "login/one_to_oneMailSending::workspaceLayout_contents");
		System.out.println("one_to_oneMailSendingDetailDelete到達");
		int rowNumber = one_to_oneMailService.deleteOneSending(form.getId());
		System.out.println("rowNumber  " + rowNumber);

		return getOne_to_oneMailSending(form2, form, model);
	}

	@GetMapping("/tweet")
	public String getTweet(@ModelAttribute TweetForm form, @ModelAttribute TweetSearchForm form1, Model model) {

		System.out.println("getTweet到達");
		int count = tweetService.count();
		model.addAttribute("tweetHomecount", count);

		model.addAttribute("contents", "login/tweet::workspaceLayout_contents");

		List<TweetDTO> tweetList = tweetService.selectMany();

		System.out.println("tweetList" + tweetList);

		//model.addAttributeは毎回保存しなおしているので変更が必要な画面などにはgetやPostでおいておくとページが切り替わって時に最新のデータをもって表示される
		//Sessionのほうは、保存されたらそれっきり変わらないので、画面の内容が切り替わるところでは使わずにmodel.addAttributeを使う
		model.addAttribute("tweetList", tweetList);

		int count2 = todo_itemsService.count();
		model.addAttribute("workspaceCount", count2);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return "/login/workspaceLayout";

	}

	@PostMapping("/tweet")
	public String postSearchTweet(@ModelAttribute TweetForm form, @ModelAttribute TweetSearchForm form1, Model model,
			List<TweetDTO> tweetList) {

		model.addAttribute("contents", "login/tweet::workspaceLayout_contents");

		model.addAttribute("tweetList", tweetList);

		model.addAttribute("tweetCount", tweetList.size());
		return "/login/workspaceLayout";
	}

	//tweetしたときpostでinsert
	@PostMapping(value = "/tweet", params = "PostTweet2")
	public String postTweet(@ModelAttribute @Validated(GroupOrder.class) TweetForm form, BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			System.out.println("Tweetエラーチェック到達");
			TweetSearchForm form1 = new TweetSearchForm();
			model.addAttribute("tweetSearchForm", form1);
			return getTweet(form, form1, model);
		}

		System.out.println("postTweet到達");
		model.addAttribute("contents", "login/tweet::workspaceLayout_contents");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth.getName());
		;

		//入力されたものを変換するのでform.のあと、getterでフィールドを呼び出す
		TweetDTO tweetdto = new TweetDTO();
		tweetdto.setId((int) form.getId());
		tweetdto.setContents((String) form.getContents());
		tweetdto.setRegistration_date((Date) form.getRegistration_date());
		tweetdto.setUser_id(auth.getName());
		;
		tweetdto.setUser_id2(auth.getName());
		boolean result = tweetService.insertOne(tweetdto);

		if (result == true) {
			System.out.println("insert成功");
		} else {
			System.out.println("insert失敗");
		}

		List<TweetDTO> tweetList = tweetService.selectMany();

		System.out.println("tweetList" + tweetList);
		if (!tweetList.isEmpty()) {
			System.out.println("select成功");
		} else {
			System.out.println("select失敗");
		}
		model.addAttribute("tweetList", tweetList);

		int count = tweetService.count();
		model.addAttribute("tweetcount", count);

		TweetSearchForm tweetSearchForm = new TweetSearchForm();
		model.addAttribute("tweetSearchForm", tweetSearchForm);

		int count2 = todo_itemsService.count();
		model.addAttribute("workspaceCount", count2);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);
		return getTweet(form, tweetSearchForm, model);

	}

	@PostMapping(value = "/tweet", params = "search")
	public String postTweetSearch(@ModelAttribute @Validated TweetSearchForm form1, BindingResult bindingResult,
			Model model) {

		model.addAttribute("contents", "login/tweet::workspaceLayout_contents");

		if (bindingResult.hasErrors()) {
			TweetForm form = new TweetForm();
			model.addAttribute("form", form);
			getTweet(form, form1, model);
		}
		System.out.println("postTweetSearch到達");
		System.out.println(form1.getUser_id());
		System.out.println(form1.getContents());
		System.out.println(form1.getRegistration_dateA());
		System.out.println(form1.getRegistration_dateZ());

		List<TweetDTO> tweetList = tweetService.search(form1.getUser_id(), form1.getContents(),
				form1.getRegistration_dateA(), form1.getRegistration_dateZ());

		System.out.println("tweetList" + tweetList);

		model.addAttribute("tweetList", tweetList);

		model.addAttribute("tweetHomecount", tweetList.size());

		TweetForm tweetForm = new TweetForm();
		model.addAttribute("tweetForm", tweetForm);

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return postSearchTweet(tweetForm, form1, model, tweetList);
	}

	@GetMapping("/tweetDetail/{id}")
	public String getTweetDetail(@ModelAttribute TweetForm form, Model model, @PathVariable("id") String Id) {
		System.out.println("postTweetDetail到達");
		model.addAttribute("contents", "login/tweetDetail::workspaceLayout_contents");

		TweetDTO tweetList = tweetService.selectOne(Id);

		System.out.println("tweetList" + tweetList);

		form.setContents(tweetList.getContents());

		model.addAttribute("tweetUser_id", tweetList.getUser_id());
		model.addAttribute("tweetContents", tweetList.getContents());
		model.addAttribute("tweetRegistration_date", tweetList.getRegistration_date());
		model.addAttribute("id", Id);
		System.out.println("modelTweetList" + tweetList);

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int count2 = tweetService.count();
		model.addAttribute("tweetcount", count2);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return "/login/workspaceLayout";
	}

	@PostMapping(value = "/tweetDetail", params = "delete")
	public String postTweetDetailDetail(@ModelAttribute TweetForm form, Model model) {
		System.out.println("postTweetDetailDetail到達");
		model.addAttribute("contents", "login/tweet::workspaceLayout_contents");
		//formからとってきたIDをもとに編集ボタンを押した列の削除を行う
		System.out.println("form.getId()" + form.getId());
		int tweetList = tweetService.deleteOne(form.getId());

		if (tweetList > 0) {
			System.out.println("削除成功");
		} else {
			System.out.println("削除失敗");
		}

		TweetSearchForm tweetSearchForm = new TweetSearchForm();
		model.addAttribute("tweetSearchForm", tweetSearchForm);

		return getTweet(null, tweetSearchForm, model);

	}

	@GetMapping("/usersNotice")
	public String getUsersNotice(@ModelAttribute AdminForm form, Model model) {

		model.addAttribute("contents", "login/usersNotice::workspaceLayout_contents");

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		return "login/workspaceLayout";

	}

	//Postでバリデーションするので@Validatedをつける
	@PostMapping("/usersNotice")
	public String postAdminNotice(@ModelAttribute @Validated(GroupOrder.class) AdminForm form,
			BindingResult bindingResult, RedirectAttributes redirectattributes, Model model) {
		System.out.println("form" + form);

		//データバインドに失敗（入力チェックに引っかかる）の場合はgetUsersNoticeに戻る
		if (bindingResult.hasErrors()) {

			return getUsersNotice(form, model);
		}
		AdminDTO admindto = new AdminDTO();
		admindto.setId((int) form.getId());
		admindto.setContents((String) form.getContents());
		admindto.setRegistration_date((Date) form.getRegistration_date());
		boolean result = adminService.insertOne(admindto);

		if (result == true) {
			System.out.println("insert成功");
		} else {
			System.out.println("insert失敗");
		}

		redirectattributes.addFlashAttribute("result", "送信完了");

		return "redirect:/usersNotice";
	}

	@GetMapping("/adminNotice")
	public String getAdminNotice(@ModelAttribute AdminForm form, @ModelAttribute AdminNoticeSearchForm form1,
			Model model) {
		System.out.println("getAdminNotice到達");

		model.addAttribute("contents", "login/adminNotice::workspaceLayout_contents");

		List<AdminDTO> adminList = adminService.selectMany();
		System.out.println("adminList" + adminList);
		//modelにadminListを登録
		model.addAttribute("adminList", adminList);

		//データ件数を取得
		int count = adminService.count();
		model.addAttribute("adminListHomeCount", count);
		model.addAttribute("adminListCount", count);

		int count2 = todo_itemsService.count();
		model.addAttribute("workspaceCount", count2);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return "login/workspaceLayout";
	}

	@GetMapping("/adminNoticeDetail/{id}")
	public String getAdminNoticeDetail(@ModelAttribute AdminForm form,
			Model model, @PathVariable("id") int id,
			String contents, Date registration_date) {

		System.out.println("adminNoticeDetail到達");
		model.addAttribute("contents", "login/adminNoticeDetail::workspaceLayout_contents");

		AdminDTO admindto = adminService.selectOne(id, contents, registration_date);

		form.setId(admindto.getId());
		form.setContents(admindto.getContents());
		form.setRegistration_date(admindto.getRegistration_date());

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return "login/workspaceLayout";

	}

	@PostMapping(value = "/adminNoticeSearch", params = "search")
	public String postAdminNoticeSearchSearch(@ModelAttribute @Validated AdminNoticeSearchForm form,
			BindingResult bindingResult,
			@RequestParam("search") String usersdto, Model model) throws ParseException {
		if (bindingResult.hasErrors()) {
			AdminForm form1 = new AdminForm();
			model.addAttribute("form1", form);
			return getAdminNotice(form1, form, model);
		}
		System.out.println("adminNoticeSearchSearch到達");

		System.out.println("contents" + form.getContents());
		System.out.println("registration_dateFrom" + form.getRegistration_dateA());
		System.out.println("registration_dateTo" + form.getRegistration_dateZ());

		model.addAttribute("contents", "login/adminNotice::workspaceLayout_contents");

		List<AdminDTO> adminList = adminService.search(form.getContents(), form.getRegistration_dateA(),
				form.getRegistration_dateZ());
		//,todo_itemsListBM
		System.out.println("adminList" + adminList);

		model.addAttribute("adminList", adminList);

		model.addAttribute("adminListHomeCount", adminList.size());

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return "login/workspaceLayout";

	}

	@PostMapping(value = "/adminNoticeDetail", params = "delete")
	public String postAdminNoticeDetailDelete(
			@ModelAttribute AdminForm form, Model model) {

		System.out.println("adminNoticeDetailDelete");
		boolean rowNumber = adminService.deleteOne(form.getId());
		System.out.println("rowNumber" + rowNumber);

		if (rowNumber == true) {
			System.out.println("削除成功");
		} else {
			System.out.println("削除失敗");
		}
		//postメソッドに@ModelAttribute AdminNoticeSearchForm adminNoticeSearchFormを記述しない代わりに、自分でインスタンス作成
		AdminNoticeSearchForm adminNoticeSearchForm = new AdminNoticeSearchForm();
		model.addAttribute("adminNoticeSearchForm", adminNoticeSearchForm);
		return getAdminNotice(null, adminNoticeSearchForm, model);

	}

	@PostMapping(value = "/adminNoticeDetail", params = "update")
	public String postAdminNoticeDetailUpdate(
			@ModelAttribute @Validated(GroupOrder.class) AdminForm form, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			int getId = form.getId();
			String getContents = form.getContents();
			Date getRegistration_date = form.getRegistration_date();
			return getAdminNoticeDetail(form, model, getId, getContents, getRegistration_date);
		}
		System.out.println("adminNoticeDetailUpdate到達");

		AdminDTO admindto = new AdminDTO();

		admindto.setId(form.getId());
		admindto.setContents(form.getContents());
		admindto.setRegistration_date(form.getRegistration_date());
		System.out.println("admindto" + admindto);

		boolean rowNumber = adminService.updateOne(admindto);
		System.out.println("rowNumber" + rowNumber);

		if (rowNumber == true) {
			System.out.println("更新成功");
		} else {
			System.out.println("更新失敗");
		}

		AdminNoticeSearchForm adminNoticeSearchForm = new AdminNoticeSearchForm();
		model.addAttribute("adminNoticeSearchForm", adminNoticeSearchForm);
		return getAdminNotice(null, adminNoticeSearchForm, model);
	}

	@GetMapping("/workaddto")
	public String getWorkaddto(@ModelAttribute WorkaddtoForm form, Model model) {

		model.addAttribute("contents", "login/workaddto::workspaceLayout_contents");

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);
		return "login/workspaceLayout";
	}

	@PostMapping("/workaddto")
	public String postWorkaddto(@ModelAttribute @Validated(GroupOrder.class) WorkaddtoForm form,
			BindingResult bindingResult, Model model) {

		//データバインドに失敗（入力チェックに引っかかる）の場合はworkaddtoに戻る
		if (bindingResult.hasErrors()) {

			//GETリクエスト用のメソッドを呼び出して、workaddtoに戻る
			return getWorkaddto(form, model);
		}

		System.out.println("form  " + form);

		//insert用変数
		Todo_itemsDTO todo_itemsdto = new Todo_itemsDTO();

		todo_itemsdto.setItem_name(form.getItem_name());
		todo_itemsdto.setUser_name(form.getUser_name());
		todo_itemsdto.setExpire_date(form.getExpire_date());
		todo_itemsdto.setDetails(form.getDetails());
		todo_itemsdto.setDetails2(form.getDetails2());
		todo_itemsdto.setDetails3(form.getDetails3());

		//work登録処理
		boolean result = todo_itemsService.insert(todo_itemsdto);

		//work登録画面の判定
		if (result == true) {
			System.out.println("insert成功");
		} else {
			System.out.println("insert失敗");
		}

		return "redirect:/workspace";

	}

	@GetMapping("/adminGuide")
	public String getAdminGuide(Model model) {
		model.addAttribute("contents", "login/adminGuide::workspaceLayout_contents");

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		return "login/workspaceLayout";
	}

	//仕様ガイド画面GET
	@GetMapping("/guide")
	public String getGuide(@ModelAttribute GuideForm form, Model model) {
		model.addAttribute("contents", "login/guide::workspaceLayout_contents");

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());

		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		return "login/workspaceLayout";
	}

	@GetMapping("/guideLogin")
	public String getGuideLogin(@ModelAttribute GuideForm form, Model model) {
		model.addAttribute("contents", "login/guideLogin::loginLayout_contents");

		return "login/loginLayout";
	}

	@GetMapping("/applicationDetail")
	public String getApplicationDetail(Model model) {
		model.addAttribute("contents", "login/applicationDetail::loginLayout_contents");

		return "login/loginLayout";
	}

	//ユーザ一覧画面用のGET用メソッド
	@GetMapping("/usersList")
	public String getUsersList(@ModelAttribute UsersSearchForm form, @ModelAttribute SignupUpdateForm form1,
			@ModelAttribute SignupForm form2, Model model) {

		model.addAttribute("contents", "login/UsersList::usersList_contents");

		String admin = "ROLE_ADMIN";

		//ユーザ一覧の生成
		List<UsersDTO> usersList = usersService.selectMany(admin);

		//Modelにユーザーリストを登録
		model.addAttribute("usersList", usersList);

		//データの件数を取得
		model.addAttribute("usersListCount", usersList.size());

		int count2 = todo_itemsService.count();
		model.addAttribute("workspaceCount", count2);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return "login/workspaceLayout";
	}

	@PostMapping("/usersList")
	public String postSearchReturnUsersList(@ModelAttribute UsersSearchForm form,
			@ModelAttribute SignupUpdateForm form1, @ModelAttribute SignupForm form2, Model model,
			List<UsersDTO> usersdto) {

		model.addAttribute("usersList", usersdto);

		//データの件数を取得
		model.addAttribute("usersListCount", usersdto.size());

		return "login/workspaceLayout";
	}

	@PostMapping(value = "/UsersList", params = "search")
	public String postWorkspace(@ModelAttribute @Validated UsersSearchForm form, BindingResult bindingResult,
			@RequestParam("search") String usersdto,
			Model model) throws ParseException {

		if (bindingResult.hasErrors()) {
			SignupUpdateForm form1 = new SignupUpdateForm();
			model.addAttribute("form1", form1);

			SignupForm form2 = new SignupForm();
			model.addAttribute("form2", form2);

			getUsersList(form, form1, form2, model);
		}
		System.out.println("usersSearchController到達");

		System.out.println("userId" + form.getUser_id()); //ユーザーID
		System.out.println("userName" + form.getUserName());//ユーザー名
		System.out.println("birthdayFrom" + form.getBirthdayA());//誕生日(始まり)
		System.out.println("birthdayTo" + form.getBirthdayZ());//誕生日(終わり)
		System.out.println("hireDateFrom" + form.getHireDateAA());//年齢～
		System.out.println("hireDateTo" + form.getHireDateZZ());//～年齢
		System.out.println("maleFemale" + form.getMaleFemale());//性別

		int maleFemale = 2;
		//==ではなくてequalsを使う
		//1が男性で0が女性を表す
		if ("1".equals(form.getMaleFemale())) {
			int i = Integer.parseInt(form.getMaleFemale());
			maleFemale = i;
		}
		if ("0".equals(form.getMaleFemale())) {
			int j = Integer.parseInt(form.getMaleFemale());
			maleFemale = j;
		}
		model.addAttribute("contents", "login/UsersList::usersList_contents");

		String admin = "ROLE_ADMIN";
		//ユーザ一覧の生成
		List<UsersDTO> usersList = usersService.search(form.getUser_id(), form.getUserName(),
				form.getBirthdayA(), form.getBirthdayZ(), form.getHireDateAA(), form.getHireDateZZ(), maleFemale,
				admin);
		System.out.println("usersList" + usersList);

		//検索後のレコードのカウントは、データベース全部ではなくて、検索時に持ってきたusersListのsizeを図ってものをmodelに登録するだけでいい。
		model.addAttribute("usersListCount", usersList.size());

		model.addAttribute("usersList", usersList);

		SignupUpdateForm signupUpdateForm = new SignupUpdateForm();
		model.addAttribute("signupUpdateForm", signupUpdateForm);

		SignupForm signupForm = new SignupForm();
		model.addAttribute("signupForm", signupForm);

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return postSearchReturnUsersList(form, signupUpdateForm, signupForm, model, usersList);
	}

	//ユーザー詳細画面のGET用メソッド
	//動的URLに対応したメソッドはGetMappingなどに/{変数名}とする
	//メアド表記の場合は上記の書き方だとうまくいかない　maruyama@yuuma.com.jpならmaruyama@yuuma.comまでしかとれない
	//それに対応するのが（"/usersdtoDetail/{id:.+}"）
	@GetMapping("/usersListDetail/{id:.+}")
	public String getUsersListDetail(@ModelAttribute UsersSearchForm form, @ModelAttribute SignupUpdateForm form1,
			@ModelAttribute SignupForm form2, Model model,//@PathVariableをつけると渡されてきたURLの値を引数の変数にいれる
			@PathVariable("id") String user_id) {
	
		//http://localhost:8080/usersdtoDetail/maruyama@yuuuma.co.jpでリクエストが来たらmaruyama@yuuma.co.jpがuser_idという変数に入れられる
		//ユーザーID確認（デバック）
		System.out.println("user_id = " + user_id);

		model.addAttribute("contents", "login/usersListDetail::usersdto_contents");

		//性別ステータス用ラジオボタンの初期化
		radioMaleFemale = initRadioMaleFemale();

		//ラジオボタン用のMapをModelに登録
		model.addAttribute("radioMaleFemale", radioMaleFemale);

		//ユーザーIDのチェック
		if (user_id != null && user_id.length() > 0) {

			//ユーザー情報を取得
			UsersDTO usersdto = usersService.selectOne(user_id);
			;

			//UsersDTOクラスをフォームクラスに変換
			form1.setUser_id(usersdto.getUser_id());
			form1.setUserName(usersdto.getUser_name());
			form1.setBirthday(usersdto.getBirthday());
			form1.setHireDate(usersdto.getHireDate());
			form1.setMaleFemale(usersdto.isMaleFemale());

			//Modelに登録
			model.addAttribute("signupForm", form1);

			int count = todo_itemsService.count();
			model.addAttribute("workspaceCount", count);

			int countTweet = tweetService.count();
			model.addAttribute("tweetcount", countTweet);

			int countAdminNotice = adminService.count();
			model.addAttribute("adminListCount", countAdminNotice);

			int countAdmin = inquiryService.count();
			model.addAttribute("inquiryListCount", countAdmin);

			Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
			System.out.println("auth" + auth2);
			System.out.println("auth.getName" + auth2.getName());

			String getName = auth2.getName();

			List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
			model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
			int rowNumber = personUsersNoticeService.count(getName);
			model.addAttribute("adminPersonNoticeCount", rowNumber);
		}
		return "login/workspaceLayout";
	}

	//ユーザー更新用処理
	//ユーザー詳細画面では更新ボタンと削除ボタンどちらを押してもusersdtoDetailにPOSTするようになっている
	//なので判別のためにparams属性があり、URLとボタンのname属性の両方を見て推移先を判断してくれる
	@PostMapping(value = "/usersListDetail", params = "update")
	public String postUsersListDetailUpdate(@ModelAttribute @Validated(GroupOrder.class) SignupUpdateForm form1,
			BindingResult bindingResult,
			@ModelAttribute UsersSearchForm form, @ModelAttribute SignupForm form2, Model model) {

		System.out.println("postUsersListDetailUpdate到達");
		//Postに行くときにバリデーションが行われるので下記のif文でエラーが出るともう一度getUsersListDetailに飛ぶようにする
		//Strign型のIDを使っているため、ここでもStringに型を変えて行う
		if (bindingResult.hasErrors()) {
			System.out.println("入力エラー");
			String s = String.valueOf(form.getUser_id());
			return getUsersListDetail(form, form1, form2, model, s);
		}
		System.out.println("更新ボタンの処理");

		//UsersDTOインスタンス作成
		UsersDTO usersdto = new UsersDTO();

		//フォームクラスをUsersDTOクラスに変更
		usersdto.setUser_id(form1.getUser_id());
		usersdto.setUser_name(form1.getUserName());
		usersdto.setBirthday(form1.getBirthday());
		usersdto.setHireDate(form1.getHireDate());
		usersdto.setMaleFemale(form1.isMaleFemale());

		try {
			//更新実行
			boolean result = usersService.updateOne(usersdto);

			if (result == true) {
				System.out.println("更新成功");
			} else {
				System.out.println("更新失敗");
			}

		} catch (DataAccessException e) {
			System.out.println("DataAccessException発生");
		}

		UsersSearchForm usersSearchForm = new UsersSearchForm();

		model.addAttribute("usersSearchForm", usersSearchForm);

		SignupForm signupForm = new SignupForm();

		model.addAttribute("signupForm", signupForm);
		return getUsersList(usersSearchForm, form1, signupForm, model);

	}

	//ユーザー削除用処理
	@PostMapping(value = "/usersListDetail", params = "delete")
	public String postUsersListDetailDelete(@ModelAttribute UsersSearchForm form,
			@ModelAttribute SignupUpdateForm form1,
			@ModelAttribute SignupForm form2, Model model) {
		System.out.println("削除用ボタンの処理");

		boolean result = usersService.deleteOne(form1.getUser_id());

		if (result == true) {
			System.out.println("削除成功");
		} else {
			System.out.println("削除失敗");
		}

		UsersSearchForm usersSearchForm = new UsersSearchForm();

		model.addAttribute("usersSearchForm", usersSearchForm);

		SignupForm signupForm = new SignupForm();

		model.addAttribute("signupForm", signupForm);

		return getUsersList(usersSearchForm, form1, signupForm, model);
	}

	@GetMapping("/workaddtoDetail/{id}")
	public String getWorkaddtoDetail(@ModelAttribute WorkaddtoDetailForm form, Model model,
			@PathVariable("id") int id) {
		System.out.println("workaddtoDetail到達");
		//ID確認（デバック）
		System.out.println("id" + id);

		model.addAttribute("contents", "login/workaddtoDetail::workspaceLayout_contents");

		if (id > 0) {

			//ユーザー情報の取得
			Todo_itemsDTO todo_itemsdto = todo_itemsService.selectOneX(id);

			System.out.println("todo_itemsdto" + todo_itemsdto);
			//Todo_itemsクラスをフォームクラスに変換
			form.setId(id);
			form.setDetails(todo_itemsdto.getDetails());
			form.setDetails2(todo_itemsdto.getDetails2());
			form.setDetails3(todo_itemsdto.getDetails3());

			System.out.println("form" + form);

			model.addAttribute("WorkaddtoForm", form);
		}

		UsersSearchForm usersSearchForm = new UsersSearchForm();

		model.addAttribute("usersSearchForm", usersSearchForm);

		SignupForm signupForm = new SignupForm();

		model.addAttribute("signupForm", signupForm);
		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return "login/workspaceLayout";
	}

	@PostMapping(value = "/workaddtoDetail", params = "update")
	public String postWorkaddtoDetailUpdate(@ModelAttribute @Validated(GroupOrder.class) WorkaddtoDetailForm form,
			BindingResult bindingResult, Model model) {

		//データバインドに失敗（入力チェックに引っかかる）の場合はworkaddtoDetailに戻る
		if (bindingResult.hasErrors()) {
			System.out.println("データバインドのif文");
			int getId = Integer.valueOf(form.getId());
			
			return getWorkaddtoDetail(form, model, getId);
		}

		System.out.println("postWorkaddtoDetail到達");

		Todo_itemsDTO todo_itemsdto = new Todo_itemsDTO();

		todo_itemsdto.setId(form.getId());
		System.out.println(todo_itemsdto.getId());
		todo_itemsdto.setDetails(form.getDetails());
		todo_itemsdto.setDetails2(form.getDetails2());
		todo_itemsdto.setDetails3(form.getDetails3());

		System.out.println("todo_itemsdto" + todo_itemsdto);

		boolean result = todo_itemsService.updateOnex(todo_itemsdto);

		if (result == true) {
			System.out.println("update成功");
		} else {
			System.out.println("update失敗");
		}

		//formを使いまわしにしているのでgetWorkspaceの引数のformのインスタンスを新たに作る
		WorkspaceSearchForm form1 = new WorkspaceSearchForm();
		model.addAttribute("workspaceSearchForm", form1);
		return getWorkspace(model, form1);
	}

	//Work登録画面の詳細画面GET用メソッド
	@GetMapping("/todo_itemsDetail/{id}")
	public String getTodo_itemsDetail(@ModelAttribute WorkaddtoForm form, Model model, @PathVariable("id") String id) {

		//ID確認（デバック）
		System.out.println("id" + id);

		//コンテンツ部分にWork登録画面の詳細を表示するための登録
		model.addAttribute("contents", "login/todo_itemsDetail::todo_itemsDetail_contents");

		//ユーザーIDのチェック
		if (id != null && id.length() > 0) {
			//ユーザー情報の取得
			Todo_itemsDTO todo_itemsdto = todo_itemsService.selectOne(id);

			System.out.println("todo_itemsdto" + todo_itemsdto);
			//Todo_itemsクラスをフォームクラスに変換
			form.setId(todo_itemsdto.getId());
			form.setUser_name(todo_itemsdto.getUser_name());
			form.setItem_name(todo_itemsdto.getItem_name());
			form.setRegistration_date(todo_itemsdto.getRegistration_date());
			form.setExpire_date(todo_itemsdto.getExpire_date());
			form.setFinished_date(todo_itemsdto.getFinished_date());

			System.out.println("form" + form);
			
			model.addAttribute("WorkaddtoForm", form);
		}

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return "login/workspaceLayout";
	}

	@PostMapping(value = "/todo_itemsDetail", params = "update")
	public String postTodo_itemsUpdate(@ModelAttribute @Validated(GroupOrder.class) WorkaddtoForm form,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			String s = String.valueOf(form.getId());
			return getTodo_itemsDetail(form, model, s);
		}
		System.out.println("更新ボタンの処理");

		//Todo_itemsのインスタンス生成
		Todo_itemsDTO todo_itemsdto = new Todo_itemsDTO();

		//formクラスをTodo_itemsDTOクラスに変換
		todo_itemsdto.setId(form.getId());
		todo_itemsdto.setUser_name(form.getUser_name());
		todo_itemsdto.setItem_name(form.getItem_name());
		todo_itemsdto.setRegistration_date(form.getRegistration_date());
		todo_itemsdto.setExpire_date(form.getExpire_date());

		System.out.println("todo_itemsdtoxxx" + todo_itemsdto);

		//更新実行
		boolean result = todo_itemsService.updateOne(todo_itemsdto);

		if (result == true) {
			System.out.println("更新成功");

		} else {
			System.out.println("更新失敗");

		}

		//formを使いまわす内容になったのでgetWorkspaceの引数のformのインスタンスを作る
		WorkspaceSearchForm form1 = new WorkspaceSearchForm();
		//インスタンスしたformをセット
		model.addAttribute("workspaceSearchForm", form1);
		return getWorkspace(model, form1);
	}

	@PostMapping(value = "/todo_itemsDetail", params = "delete")
	public String getTodo_itemsDetailDelete(@ModelAttribute WorkaddtoForm form, Model model) {

		System.out.println("削除ボタンの処理");

		boolean result = todo_itemsService.deleteOne(form.getId());

		if (result == true) {
			System.out.println("削除成功");
		} else {
			System.out.println("削除失敗");
		}

		WorkspaceSearchForm form1 = new WorkspaceSearchForm();

		model.addAttribute("workspaceSearchForm", form1);

		return getWorkspace(model, form1);

	}

	@PostMapping(value = "/todo_itemsDetail", params = "completed")
	public String getTodo_itemsDetail(@ModelAttribute WorkaddtoForm form, Model model) {

		System.out.println("完了ボタンの処理");

		//String型に変更
		String getStringId = String.valueOf(form.getId());
		
		Todo_itemsDTO todo_itemsList = todo_itemsService.selectOne(getStringId);

		System.out.println("todo_itemsList" + todo_itemsList);

		//完了日がセットされていなかったらnullにする
		if (todo_itemsList.getFinished_date() != null) {

			//idと、未完了にしたいのでnullを渡している
			boolean isCompletedTodo_items = todo_itemsService.completedOne(form.getId(), null);
			
			if (isCompletedTodo_items == true) {
				System.out.println("未完了に変更完了");
			} else {
				System.out.println("処理失敗");
			}
		} else {
			System.out.println("else到着");

			Date dateObj = new Date();
			//idと、dateObjを渡している
			boolean isCompletedTodo_items2 = todo_itemsService.completedOne(form.getId(), dateObj);
			
			if (isCompletedTodo_items2 == true) {
				System.out.println("完了日設定完了");
			}
		}
		WorkspaceSearchForm form1 = new WorkspaceSearchForm();

		model.addAttribute("workspaceSearchForm", form1);

		return getWorkspace(model, form1);
	}

	//問い合わせフォームGet用メソッド
	@GetMapping("inquiry")
	public String getInquiry(@ModelAttribute InquiryForm form, Model model) {

		//コンテンツ部分にユーザー詳細を表示するための文字列を登録
		model.addAttribute("contents", "login/inquiry::workspaceLayout_contents");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("auth" + auth);
		System.out.println("auth.getName" + auth.getName());

		form.setUser_id(auth.getName());
		model.addAttribute("inquiryForm", form);

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return "login/workspaceLayout";
	}

	//問い合わせフォームPost用メソッド
	@PostMapping("inquiry")
	//Validated実施
	public String postInquiry(@ModelAttribute @Validated(GroupOrder.class) InquiryForm form,
			BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

		if (bindingResult.hasErrors()) {

			return getInquiry(form, model);
		}

		String user_idT = null;
		if ("true".equals(form.getUser_idT())) {
			user_idT = form.getUser_id();
		}

		System.out.println(form);

		//insert用変数
		InquiryDTO inquirydto = new InquiryDTO();

		inquirydto.setId(form.getId());
		inquirydto.setTitle(form.getTitle());
		inquirydto.setContent(form.getContent());
		//inquirydto.setRegistration_date(form.getRegistration_date());
		inquirydto.setMail(form.getMail());

		boolean result = inquiryService.insert(inquirydto, user_idT);

		//ユーザー登録処理
		if (result == true) {
			System.out.println("insert成功");
		} else {
			System.out.println("insert失敗");
		}

		redirectAttributes.addFlashAttribute("result", "ご意見ありがとうございます");

		return "redirect:/inquiry";
	}

	@GetMapping("inquiryLogin")
	public String getInquiryLogin(@ModelAttribute InquiryForm form, Model model) {
		model.addAttribute("contents", "login/inquiryLogin::loginLayout_contents");
		return "login/loginLayout";
	}

	@PostMapping("/inquiryLogin")
	public String postInquryLogin(Model model, @ModelAttribute @Validated(GroupOrder.class) InquiryForm form,
			BindingResult bindingResult, RedirectAttributes redirectAttribute) {

		if (bindingResult.hasErrors()) {

			return getInquiryLogin(form, model);
		}

		String user_idT = null;
		if ("true".equals(form.getUser_idT())) {
			user_idT = form.getUser_id();
		}

		System.out.println(form);
		
		InquiryDTO inquirydto = new InquiryDTO();

		inquirydto.setId(form.getId());
		inquirydto.setTitle(form.getTitle());
		inquirydto.setContent(form.getContent());
		//inquirydto.setRegistration_date(form.getRegistration_date());
		inquirydto.setMail(form.getMail());

		boolean result = inquiryService.insertLogin(inquirydto,user_idT);

		if (result == true) {
			System.out.println("insert成功");
		} else {
			System.out.println("insert失敗");
		}

		redirectAttribute.addFlashAttribute("result", "ご意見ありがとうございます。");

		return "redirect:/inquiryLogin";
	}

	//admin一覧画面用のGET用メソッド
	@GetMapping("/admin")
	public String getAdmin(@ModelAttribute InquiryForm form, @ModelAttribute InquirySearchForm form2, Model model) {

		System.out.println("getAdmin到達");
		
		model.addAttribute("contents", "login/admin::workspaceLayout_contents");
		//admin一覧画面の生成
		List<InquiryDTO> inquiryList = inquiryService.selectMany();

		System.out.println("controllerinquiryList" + inquiryList);
		//modelにinquiryListを登録
		model.addAttribute("inquiryList", inquiryList);
		//問い合わせ件数を取得
		int count = inquiryService.count();
		model.addAttribute("inquiryListCount", count);

		int count2 = todo_itemsService.count();
		model.addAttribute("workspaceCount", count2);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		return "login/workspaceLayout";
	}

	@PostMapping("/admin")
	public String postAdmin(@ModelAttribute InquiryForm form, InquirySearchForm form1, Model model,
			List<InquiryDTO> inquiryList) {

		model.addAttribute("inquiryList", inquiryList);

		model.addAttribute("inquiryCount", inquiryList.size());

		return "login/workspaceLayout";
	}

	//お問い合わせの詳細画面GET用メソッド
	@GetMapping("/adminDetail/{id}")
	public String getAdminDetail(@ModelAttribute InquiryForm form, Model model, @PathVariable("id") String id) {

		//ID確認（デバック）
		System.out.println("id" + id);

	
		model.addAttribute("contents", "login/adminDetail::adminDetail_contents");

		//ユーザーIDのチェック
		if (id != null && id.length() > 0) {
		
			InquiryDTO inquiryList = inquiryService.selectOne(id);

			System.out.println("cotrolinquirydto" + inquiryList);
			;
			//inquiryListをフォームクラスに変換
			form.setId(inquiryList.getId());
			form.setTitle(inquiryList.getTitle());
			form.setContent(inquiryList.getContent());
			form.setRegistration_date(inquiryList.getRegistration_date());
			form.setFinished_date(inquiryList.getFinished_date());

			System.out.println("form" + form);
			//Modelに登録
			model.addAttribute("inquiryform", form);

			//selectOneしてきたところのデータをテーブルに入れる
			model.addAttribute("adminTitle", inquiryList.getTitle());
			model.addAttribute("adminContent", inquiryList.getContent());
			model.addAttribute("id", id);
			model.addAttribute("adminRegistration_date", inquiryList.getRegistration_date());

			int count = todo_itemsService.count();
			model.addAttribute("workspaceCount", count);

			int countTweet = tweetService.count();
			model.addAttribute("tweetcount", countTweet);

			int countAdminNotice = adminService.count();
			model.addAttribute("adminListCount", countAdminNotice);

			int countAdmin = inquiryService.count();
			model.addAttribute("inquiryListCount", countAdmin);
		}

		return "login/workspaceLayout";
	}

	@PostMapping(value = "/admin", params = "search")
	public String postInquiry(@ModelAttribute @Validated InquirySearchForm form1, BindingResult bindingResult,
			@RequestParam("search") String usersdto,
			Model model) {

		if (bindingResult.hasErrors()) {
			InquiryForm form = new InquiryForm();
			model.addAttribute("form", form);
			return getAdmin(form, form1, model);
		}
		System.out.println("adminSearch到達");

		System.out.println(form1.getTitle());
		System.out.println(form1.getContent());
		System.out.println(form1.getRegistration_dateA());
		System.out.println(form1.getRegistration_dateZ());
		System.out.println(form1.getFinished_dateA());
		System.out.println(form1.getFinished_dateZ());
		System.out.println(form1.getFinished_dateT());

		model.addAttribute("contents", "login/admin::workspaceLayout_contents");

		List<InquiryDTO> inquiryList = inquiryService.search(form1.getTitle(), form1.getContent(),
				form1.getRegistration_dateA(), form1.getRegistration_dateZ(), form1.getFinished_dateA(),
				form1.getFinished_dateZ(), form1.getFinished_dateT());
		System.out.println("inquiryList" + inquiryList);

		model.addAttribute("inquiryList", inquiryList);

		model.addAttribute("inquiryListCount", inquiryList.size());

		InquiryForm inquiryForm = new InquiryForm();
		model.addAttribute("inquiryForm", inquiryForm);

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		return postAdmin(inquiryForm, form1, model, inquiryList);

	}

	@PostMapping(value = "/adminDetail", params = "completed")
	public String getAdminDetail(@ModelAttribute InquiryForm form, Model model) {

		System.out.println("完了ボタンの処理");

		String getStringId = String.valueOf(form.getId());

		
		InquiryDTO inquiryList = inquiryService.selectOne(getStringId);

		System.out.println("inquiryList" + inquiryList);

		//完了日がセットされていたらnullにする
		if (inquiryList.getFinished_date() != null) {
			System.out.println("if文到達");

			//データベースの完了切替更新メソッド ServiceのほうでdaoにアクセスしてSQL文を実行してから変わったレコード数でtrueかfalseかを決めている
			//idと、未完了にしたいのでnullを渡している
			boolean isCompletedInquiry = inquiryService.completedOne(form.getId(), null);
			if (isCompletedInquiry == true) {
				System.out.println("未完了に設定変更");
			} else {
				System.out.println("処理失敗");
			}
			//データオブジェクトを作ってnullだったら現在の日付（完了日）を入れる
		} else {
			Date dateObj = new Date();
			//idと、dateObjを渡している
			boolean isCompletedInquiry2 = inquiryService.completedOne(form.getId(), dateObj);
		
			if (isCompletedInquiry2 == true) {
				System.out.println("完了日設定完了");
			} else {
				System.out.println("処理失敗");
			}
		}

		InquirySearchForm inquirySearchForm = new InquirySearchForm();
		model.addAttribute("inquirySearchForm", inquirySearchForm);
		return getAdmin(form, inquirySearchForm, model);
	}

	@PostMapping(value = "/adminDetail", params = "delete")
	public String getAdminDetailDelete(@ModelAttribute InquiryForm form, Model model) {

		System.out.println("削除ボタンの処理");

		boolean result = inquiryService.deleteOne(form.getId());

		System.out.println("controlresult  " + result);
		if (result == true) {
			System.out.println("削除成功");
		} else {
			System.out.println("削除失敗");
		}

		InquirySearchForm inquirySearchForm = new InquirySearchForm();
		model.addAttribute("inquirySearchForm", inquirySearchForm);

		return getAdmin(form, inquirySearchForm, model);
	}

	@GetMapping("/adminPersonNotice")
	public String getAdminPersonNotice(@ModelAttribute AdminPersonNoticeSearchForm form1,
			@ModelAttribute AdminPersonNoticeDetailForm form, Model model) {
		System.out.println("adminPersonNotice到達");
		model.addAttribute("contents", "login/adminPersonNotice::workspaceLayout_contents");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("auth.getName" + auth.getName());

		String getName = auth.getName();

		int rowNumber = personUsersNoticeService.count(getName);

		model.addAttribute("adminPersonNoticeHomeCount", rowNumber);

		model.addAttribute("adminPersonNoticeCount", rowNumber);

		List<PersonUsersNoticeDTO> personUsersNoticeList = personUsersNoticeService.selectMany(getName);
		System.out.println("personUsersNoticeList" + personUsersNoticeList);

		model.addAttribute("personUsersNoticeList", personUsersNoticeList);

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());

		return "login/workspaceLayout";
	}

	@PostMapping("/adminPersonNotice")
	public String postAdminPersonNotice(@ModelAttribute AdminPersonNoticeSearchForm form,
			@ModelAttribute AdminPersonNoticeDetailForm form1, Model model) {
		model.addAttribute("contents", "login/adminPersonNotice::workspaceLayout_contents");

		return "login/workspaceLayout";

	}

	@GetMapping("/adminPersonNoticeDetail/{id}")
	public String getAdminPersonNoticeDetail(@ModelAttribute AdminPersonNoticeDetailForm form, Model model,
			@PathVariable("id") int Id) {
		model.addAttribute("contents", "login/adminPersonNoticeDetail::workspaceLayout_contents");

		PersonUsersNoticeDTO personusersnoticeList = personUsersNoticeService.selectOne(Id);

		System.out.println("personusersnoticeList" + personusersnoticeList);

		personusersnoticeList.getContent();

		System.out.println("personusersnoticeList.getContent();" + personusersnoticeList.getContent());

		model.addAttribute("personusersnoticeContent", personusersnoticeList.getContent());
		model.addAttribute("personusersnoticeRegistration_date", personusersnoticeList.getRegistration_date());
		model.addAttribute("id", Id);

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return "login/workspaceLayout";

	}

	@PostMapping(value = "/adminPersonNoticeDetail", params = "delete")
	public String postAdminPersonNoticeDetail(@ModelAttribute AdminPersonNoticeDetailForm form, Model model) {

		int rowNumber = personUsersNoticeService.deleteOne(form.getId());

		if (rowNumber > 0) {
			System.out.println("削除成功");
		} else {
			System.out.println("削除失敗");
		}

		AdminPersonNoticeSearchForm adminpersonnoticesearchform = new AdminPersonNoticeSearchForm();
		model.addAttribute("adminPersonNoticeSearchForm", adminpersonnoticesearchform);

		return getAdminPersonNotice(adminpersonnoticesearchform, form, model);

	}

	@PostMapping(value = "/adminPersonNotice", params = "search")
	public String postAdminPersonNotice(@ModelAttribute @Validated AdminPersonNoticeSearchForm form,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			AdminPersonNoticeDetailForm form1 = new AdminPersonNoticeDetailForm();
			model.addAttribute("form1", form1);
			return getAdminPersonNotice(form, form1, model);
		}
		System.out.println("form.getContent" + form.getContent());
		System.out.println("form.getRegistration_DateFrom" + form.getRegistration_dateA());
		System.out.println("form.getRegistration_DateTo" + form.getRegistration_dateZ());

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("auth.getName" + auth.getName());

		String getName = auth.getName();

		List<PersonUsersNoticeDTO> personUsersNoticeList = personUsersNoticeService.search(form.getContent(),
				form.getRegistration_dateA(), form.getRegistration_dateZ(), getName);

		System.out.println("personUsersNoticeList" + personUsersNoticeList);

		model.addAttribute("personUsersNoticeList", personUsersNoticeList);

		AdminPersonNoticeDetailForm form1 = new AdminPersonNoticeDetailForm();
		model.addAttribute("form1", form1);

		model.addAttribute("adminPersonNoticeHomeCount", personUsersNoticeList.size());

		int countpersonUsersNoticeList = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", countpersonUsersNoticeList);

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());

		return postAdminPersonNotice(form, form1, model);

	}

	@GetMapping("/personUsersNotice")
	public String getPersonUsersNotice(@ModelAttribute PersonUsersNoticeSearchForm form, Model model) {
		model.addAttribute("contents", "login/personUsersNotice::workspaceLayout_contents");

		String admin = "ROLE_ADMIN";

		List<UsersDTO> usersList = usersService.selectManyUsersNotice(admin);

		int count = usersService.countPersonUsersNotice();

		model.addAttribute("usersHomeCount", count);
		model.addAttribute("usersList", usersList);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		int countAdminPersonNotice = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeHomeCount", countAdminPersonNotice);
		model.addAttribute("adminPersonNoticeCount", countAdminPersonNotice);

		int count2 = todo_itemsService.count();
		model.addAttribute("workspaceCount", count2);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		return "login/workspaceLayout";
	}

	@PostMapping("/personUsersNotice")
	public String postPersonUsersNotice(@ModelAttribute PersonUsersNoticeSearchForm form, Model model) {
		model.addAttribute("contents", "login/personUsersNotice::workspaceLayout_contents");

		return "login/workspaceLayout";
	}

	@PostMapping(value = "/personUsersNotice", params = "search")
	public String postPersonUsersNoticeSearch(@ModelAttribute @Validated PersonUsersNoticeSearchForm form,
			BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {

			return getPersonUsersNotice(form, model);
		}

		System.out.println("user_id" + form.getUser_id());
		System.out.println("user_name" + form.getUser_name());

		String admin = "ROLE_ADMIN";
		List<UsersDTO> usersList = usersService.searchPersonUsersNotice(form.getUser_id(), form.getUser_name(), admin);

		System.out.println("usersList" + usersList);
		model.addAttribute("usersList", usersList);

		model.addAttribute("adminPersonNoticeHomeCount", usersList.size());

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		int countAdminPersonNotice = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", countAdminPersonNotice);

		return postPersonUsersNotice(form, model);
	}

	@GetMapping("/personUsersNoticeSending")
	public String getPersonUsersNoticeSending(@ModelAttribute PersonUsersNoticeSendingForm form,
			PersonUsersNoticeSendingSearchForm form2, Model model) {
		model.addAttribute("contents", "login/personUsersNoticeSending::workspaceLayout_contents");

		List<PersonUsersNoticeDTO> personusersnoticeList = personUsersNoticeService.selectMany();

		System.out.println("personusersnoticeList    " + personusersnoticeList);

		model.addAttribute("personusersnoticesendingList", personusersnoticeList);

		int count = personUsersNoticeService.countSending();

		model.addAttribute("personusersnoticesendingCount", count);

		int count2 = todo_itemsService.count();
		model.addAttribute("workspaceCount", count2);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);
		return "login/workspaceLayout";
	}

	@PostMapping(value = "/personUsersNoticeSending", params = "search")
	public String postPersonUsersNoticeSendingSearch(@ModelAttribute @Validated PersonUsersNoticeSendingSearchForm form,
			BindingResult bindingResult, Model model) {
		model.addAttribute("contents", "login/personUsersNoticeSending::workspaceLayout_contents");

		if (bindingResult.hasErrors()) {
			PersonUsersNoticeSendingForm personusersnoticesendingform = new PersonUsersNoticeSendingForm();
			model.addAttribute("personusersnoticesendingform", personusersnoticesendingform);
			getPersonUsersNoticeSending(personusersnoticesendingform, form, model);
		}

		List<PersonUsersNoticeDTO> personusersnoticedto = personUsersNoticeService.searchSending(form.getUser_id(),
				form.getContent(), form.getRegistration_dateFrom(), form.getRegistration_dateTo());
		System.out.println("personusersnoticedto(workcontroller)   " + personusersnoticedto);
		model.addAttribute("personusersnoticesendingList", personusersnoticedto);

		model.addAttribute("personusersnoticesendingCount", personusersnoticedto.size());

		int count = personUsersNoticeService.countSending();

		model.addAttribute("personusersnoticesendingCount", count);

		int count2 = todo_itemsService.count();
		model.addAttribute("workspaceCount", count2);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		return "login/workspaceLayout";
	}

	@GetMapping("/personUsersNoticeSendingDetail/{id}")
	public String getPersonUsersNoticeSendingDetail(@ModelAttribute PersonUsersNoticeSendingDetailForm form,
			Model model, @PathVariable("id") int id) {
		model.addAttribute("contents", "login/personUsersNoticeSendingDetail::workspaceLayout_contents");
		PersonUsersNoticeDTO personusersnoticeList = personUsersNoticeService.selectOneSendingDetail(id);
		System.out.println("personusersnoticeList    " + personusersnoticeList);
		model.addAttribute("getContent", personusersnoticeList.getContent());
		model.addAttribute("getUser_id", personusersnoticeList.getUser_id());
		model.addAttribute("getRegistration_date", personusersnoticeList.getRegistration_date());
		model.addAttribute("id", personusersnoticeList.getId());

		int count2 = todo_itemsService.count();
		model.addAttribute("workspaceCount", count2);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		return "login/workspaceLayout";

	}

	@PostMapping(value = "/personUsersNoticeSendingDetail", params = "delete")
	public String postPersonUsersNoticeSendingDetail(@ModelAttribute PersonUsersNoticeSendingDetailForm form,
			PersonUsersNoticeSendingSearchForm form2, Model model) {
		model.addAttribute("contents", "login/personUsersNoticeSendingDetail::workspaceLayout_contents");

		int rowNumber = personUsersNoticeService.deleteOneSendingDetail(form.getId());
		if (rowNumber > 0) {
			System.out.println("削除成功");
		} else {
			System.out.println("削除失敗");
		}
		PersonUsersNoticeSendingForm personusersnoticesendingform = new PersonUsersNoticeSendingForm();
		model.addAttribute("personusersnoticesendingform", personusersnoticesendingform);
		return getPersonUsersNoticeSending(personusersnoticesendingform, form2, model);

	}

	@GetMapping("/personUsersNoticeDetail/{user_id}")
	public String getPersonUsersnoticeDetail(@ModelAttribute PersonUsersNoticeInquiryForm form, Model model,
			@PathVariable("user_id") String user_id) {
		model.addAttribute("contents", "login/personUsersNoticeDetail::workspaceLayout_contents");

		model.addAttribute("hiddenUser_id", user_id);

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		return "login/workspaceLayout";
	}

	@PostMapping("/personUsersNoticeDetail")
	public String postPersonUsersNoticeDetail(
			@ModelAttribute @Validated(GroupOrder.class) PersonUsersNoticeInquiryForm form, BindingResult bindingResult,
			Model model) {
		model.addAttribute("contents", "login/personUsersNoticeDetail::workspaceLayout_contents");

		if (bindingResult.hasErrors()) {

			String getUser_id = String.valueOf(form.getUser_id());
			return getPersonUsersnoticeDetail(form, model, getUser_id);
		}

		System.out.println("personUsersNoticeDetail到達");
		System.out.println("getUser_id" + form.getUser_id());
		PersonUsersNoticeDTO personusersnoticedto = new PersonUsersNoticeDTO();
		personusersnoticedto.setUser_id((String) form.getUser_id());
		personusersnoticedto.setContent((String) form.getContent());

		boolean rowNumber = personUsersNoticeService.insertOne(personusersnoticedto);

		System.out.println("rowNumber" + rowNumber);

		if (rowNumber == true) {
			System.out.println("insert成功");
		} else {
			System.out.println("insert失敗");
		}

		model.addAttribute("result", "送信完了");

		String s = String.valueOf(form.getUser_id());
		return getPersonUsersnoticeDetail(form, model, s);

	}

	@GetMapping("/personMemo")
	public String getPersonMemo(@ModelAttribute PersonMemoForm form, @ModelAttribute PersonMemoSearchForm form1,
			Model model) {

		System.out.println("personMemo到達");
		model.addAttribute("contents", "login/personMemo::workspaceLayout_contents");

		int count = personMemoService.count();

		model.addAttribute("personmemoCount", count);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("auth" + auth);
		System.out.println("auth.getName" + auth.getName());

		String getName = auth.getName();

		List<PersonMemoDTO> personMemoList = personMemoService.selectMany(getName);

		System.out.println("personMemoList" + personMemoList);
		if (personMemoList != null) {
			System.out.println("select成功");
		} else {
			System.out.println("select失敗");
		}

		model.addAttribute("personMemoList", personMemoList);

		model.addAttribute("personmemoCount", personMemoList.size());

		int count2 = todo_itemsService.count();
		model.addAttribute("workspaceCount", count2);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return "login/workspaceLayout";
	}

	@PostMapping("/personMemoSearch")
	public String postPersonMemoSearch(@ModelAttribute PersonMemoSearchForm form, Model model,
			@ModelAttribute PersonMemoForm form1) {

		model.addAttribute("contents", "login/personMemo::workspaceLayout_contents");

		return "login/workspaceLayout";
	}

	@GetMapping("/personMemoAddto")
	public String getPersonMemoAddto(@ModelAttribute PersonMemoForm form, Model model) {

		System.out.println("getPersonMemoAddto到達");
		model.addAttribute("contents", "login/personMemoAddto::workspaceLayout_contents");
		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return "login/workspaceLayout";
	}

	@PostMapping("/personMemoAddto")
	public String postPersonMemoAddto(@ModelAttribute @Validated(GroupOrder.class) PersonMemoForm form,
			BindingResult bindingResult, RedirectAttributes redirectattributes,
			Model model) {
		if (bindingResult.hasErrors()) {

			return getPersonMemoAddto(form, model);
		}

		System.out.println("postPersonMemoAddto到達");

		model.addAttribute("contents", "login/personMemoAddto::workspaceLayout_contents");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth.getName());//ログイン者のメールアドレスを取得

		PersonMemoDTO personmemodto = new PersonMemoDTO();

		personmemodto.setMemo(form.getMemo());
		personmemodto.setUser_id(auth.getName());

		boolean result = personMemoService.insert(personmemodto);

		System.out.println("result" + result);
		if (result == true) {
			System.out.println("insert成功");
		} else {
			System.out.println("insert失敗");
		}
		return "redirect:/personMemo";

	}

	@GetMapping("/personMemoDetail/{id}")
	public String getPersonMemoDetail(@ModelAttribute PersonMemoForm form, Model model, @PathVariable("id") int Id) {
		System.out.println("getPersonMemoDetail到達");

		model.addAttribute("contents", "login/personMemoDetail::workspaceLayout_contents");

		PersonMemoDTO personmemoList = personMemoService.selectOne(Id);
		System.out.println("personmemoList" + personmemoList);

		model.addAttribute("personmemolistMemo", personmemoList.getMemo());
		model.addAttribute("personmemolistRegistration_date", personmemoList.getRegistration_date());
		model.addAttribute("id", Id);

		form.setMemo(personmemoList.getMemo());
		form.setFinished_date(personmemoList.getFinished_date());
		model.addAttribute("memo", form);

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return "login/workspaceLayout";
	}

	@PostMapping(value = "/personMemoDetail", params = "delete")
	public String postPersonMemoDetailDelete(@ModelAttribute PersonMemoForm form, Model model) {
		System.out.println("personMemoDetailSearch到達");

		model.addAttribute("contents", "login/personMemoDetail::workspaceLayout_contents");

		int rowNumber = personMemoService.deleteOne(form.getId());

		if (rowNumber > 0) {
			System.out.println("deleteOne成功");
		} else {
			System.out.println("deleteOne失敗");
		}

		PersonMemoSearchForm personmemosearchform = new PersonMemoSearchForm();
		//model.addAttributeの第一引数の名前をhtmlが探す
		model.addAttribute("personMemoSearchForm", personmemosearchform);
		return getPersonMemo(form, personmemosearchform, model);
	}

	@PostMapping(value = "/personMemoDetail", params = "completed")
	public String postPersonMemoDetailCompleted(@ModelAttribute PersonMemoForm form, Model model) {
		System.out.println("personMemoDetailCompleted");

		model.addAttribute("contents", "login/personMemoDetail::workspaceLayout_contents");

		String getId = String.valueOf(form.getId());
		PersonMemoDTO personmemoList = personMemoService.selectOneCompleted(getId);

		if (personmemoList.getFinished_date() != null) {

			boolean result = personMemoService.completed(form.getId(), null);

			if (result == true) {
				System.out.println("未完了にするボタン成功");
			} else {
				System.out.println("未完了にするボタン失敗");
			}

		} else {

			Date dateObj = new Date();
			boolean result = personMemoService.completed(form.getId(), dateObj);

			if (result == true) {
				System.out.println("完了にするボタン成功");
			} else {
				System.out.println("完了にするボタン失敗");
			}
		}

		PersonMemoSearchForm personmemosearchform = new PersonMemoSearchForm();
		model.addAttribute("personMemoSearchForm", personmemosearchform);
		return getPersonMemo(form, personmemosearchform, model);
	}

	@PostMapping(value = "/personMemoDetail", params = "update")
	public String postPersonmemoDetailUpdate(@ModelAttribute @Validated(GroupOrder.class) PersonMemoForm form,
			BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			int getId = form.getId();
			return getPersonMemoDetail(form, model, getId);
		}

		System.out.println("personMemoDetailUpdate到達");

		model.addAttribute("contents", "login/personMemoDetail::workspaceLayout_contents");

		//インスタンスを作ってformの値を入れていき、それをサービスに渡す
		PersonMemoDTO personmemodto = new PersonMemoDTO();

		personmemodto.setId(form.getId());
		personmemodto.setMemo(form.getMemo());
		System.out.println("personmemodto" + personmemodto);
		boolean result = personMemoService.updateOne(personmemodto);
		System.out.println("result" + result);

		PersonMemoSearchForm personmemosearchform = new PersonMemoSearchForm();
		model.addAttribute("personMemoSearchForm", personmemosearchform);

		return getPersonMemo(form, personmemosearchform, model);
	}

	@PostMapping(value = "/personMemoDetail", params = "search")
	public String postPersonMemoDetailSearch(@ModelAttribute @Validated PersonMemoSearchForm form,
			BindingResult bindingResult, Model model) {
		System.out.println("personMemoDetailSearch到達");

		if (bindingResult.hasErrors()) {
			System.out.println("personMemoDetailSearchエラーメッセージ到達");
			PersonMemoForm personmemoform = new PersonMemoForm();
			model.addAttribute("personmemoform", personmemoform);
			return getPersonMemo(personmemoform, form, model);
		}

		System.out.println(form.getMemo());
		System.out.println(form.getRegistration_dateA());
		System.out.println(form.getRegistration_dateZ());
		System.out.println(form.getFinished_dateA());
		System.out.println(form.getFinished_dateZ());
		System.out.println(form.getFinished_dateT());

		String Finished_dateT = null;

		if ("true".equals(form.getFinished_dateT())) {
			String date = form.getFinished_dateT();

			Finished_dateT = date;

		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("auth" + auth);
		System.out.println("auth.getName" + auth.getName());

		String getName = auth.getName();

		List<PersonMemoDTO> personMemoList = personMemoService.search(form.getMemo(), form.getRegistration_dateA(),
				form.getRegistration_dateZ(), form.getFinished_dateA(), form.getFinished_dateZ(), Finished_dateT,
				getName);

		System.out.println("personMemoList" + personMemoList);

		model.addAttribute("personMemoList", personMemoList);

		PersonMemoForm personmemoform = new PersonMemoForm();
		model.addAttribute("personmemoform", personmemoform);

		model.addAttribute("personmemoCount", personMemoList.size());

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return postPersonMemoSearch(form, model, personmemoform);
	}

	@GetMapping("snsShare")
	public String getSnsShare(Model model) {
		model.addAttribute("contents", "login/snsShare::workspaceLayout_contents");

		int count = todo_itemsService.count();
		model.addAttribute("workspaceCount", count);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		List<One_to_oneMailDTO> one_to_onemailList = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailList.size());
		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		return "login/workspaceLayout";
	}

	@GetMapping("/snsShareLogin")
	public String getSnsShareLogin(Model model) {
		model.addAttribute("contents", "login/snsShareLogin::loginLayout_contents");

		return "login/loginLayout";
	}

	@GetMapping("/client")
	public String getClient(@ModelAttribute ClientSearchForm form, ClientDetailForm form2, ClientDeleteForm form3,
			Model model) {
		model.addAttribute("contents", "login/client::workspaceLayout_contents");
		List<ClientDTO> clientList = clientService.selectMany();
		System.out.println("clientList   " + clientList);
		model.addAttribute("clientList", clientList);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		int count = clientService.count();
		model.addAttribute("clientListCount", count);

		int countTodo_itmes = todo_itemsService.count();
		model.addAttribute("workspaceCount", countTodo_itmes);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		List<One_to_oneMailDTO> one_to_onemailCount = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailCount.size());

		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		return "login/workspaceLayout";

	}

	@GetMapping("/clientAddto")
	public String getClientAddto(@ModelAttribute ClientAddtoForm form, Model model) {
		model.addAttribute("contents", "login/clientAddto::workspaceLayout_contents");

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		int countTodo_itmes = todo_itemsService.count();
		model.addAttribute("workspaceCount", countTodo_itmes);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		List<One_to_oneMailDTO> one_to_onemailCount = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailCount.size());

		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);
		return "login/workspaceLayout";
	}

	@PostMapping("/clientAddto")
	public String postClientAddto(@ModelAttribute @Validated(GroupOrder.class) ClientAddtoForm form,
			BindingResult bindingResult, ClientSearchForm form2, Model model) {
		model.addAttribute("contents", "login/client::workspaceLayout_contents");

		if (bindingResult.hasErrors()) {
			System.out.println("エラーチェック到達");
			ClientDetailForm form1 = new ClientDetailForm();
			model.addAttribute("clientDetailForm", form1);
			ClientDeleteForm form3 = new ClientDeleteForm();
			model.addAttribute("clientDeleteForm", form3);
			return getClientAddto(form, model);
		}

		ClientDTO clientdto = new ClientDTO();
		clientdto.setId(form.getId());
		clientdto.setUser_name(form.getUser_name());
		clientdto.setMailaddress(form.getMailaddress());
		clientdto.setAddress(form.getAddress());
		clientdto.setTelephone(form.getTelephone());
		clientdto.setRegistration_date(form.getRegistration_date());
		clientdto.setCompany(form.getCompany());

		int rowNumber = clientService.insertOne(clientdto);

		if (rowNumber > 0) {
			System.out.println("insert成功");
		} else {
			System.out.println("insert失敗");
		}

		ClientDetailForm clientdetailform = new ClientDetailForm();
		model.addAttribute("clientdetailform", clientdetailform);

		ClientDeleteForm clientdeleteform = new ClientDeleteForm();
		model.addAttribute("clientdeleteform", clientdeleteform);

		return getClient(form2, clientdetailform, clientdeleteform, model);

	}

	@GetMapping("/clientDetail/{id}")
	public String getClientDetail(@ModelAttribute ClientDetailForm form, Model model, @PathVariable("id") int id) {
		model.addAttribute("contents", "login/clientDetail::workspaceLayout_contents");

		ClientDTO clientList = clientService.selectOne(form.getId());
		System.out.println("clientList   " + clientList);
		model.addAttribute("clientList", clientList);
		model.addAttribute("clientCompany", clientList.getCompany());
		model.addAttribute("clientAddress", clientList.getAddress());
		form.setUser_name(clientList.getUser_name());
		model.addAttribute("memo", form.getUser_name());
		form.setTelephone(clientList.getTelephone());
		model.addAttribute("telephone", form.getTelephone());
		form.setMailaddress(clientList.getMailaddress());
		model.addAttribute("mailaddress", form.getMailaddress());
		model.addAttribute("clientRegistration_date", clientList.getRegistration_date());
		model.addAttribute("id", id);
		ClientSearchForm clientsearchform = new ClientSearchForm();
		model.addAttribute("clientsearchform", clientsearchform);

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		int countTodo_itmes = todo_itemsService.count();
		model.addAttribute("workspaceCount", countTodo_itmes);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		List<One_to_oneMailDTO> one_to_onemailCount = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailCount.size());

		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);
		return "login/workspaceLayout";
	}

	@PostMapping(value = "/client", params = "search")
	public String postClientSearch(@ModelAttribute @Validated ClientSearchForm form, BindingResult bindingResult,
			Model model) {

		if (bindingResult.hasErrors()) {
			System.out.println("エラーチェック到達");
			ClientDetailForm form2 = new ClientDetailForm();
			model.addAttribute("clientDetailForm", form2);
			ClientDeleteForm form3 = new ClientDeleteForm();
			model.addAttribute("clientDeleteForm", form3);
			return getClient(form, form2, form3, model);
		}

		model.addAttribute("contents", "login/client::workspaceLayout_contents");
		List<ClientDTO> clientList = clientService.search(form.getCompany(), form.getAddress(), form.getUser_name(),
				form.getRegistration_dateFrom(), form.getRegistration_dateTo(), form.getTelephone(),
				form.getMailaddress());
		System.out.println("clientList  " + clientList);
		model.addAttribute("clientList", clientList);
		model.addAttribute("clientListCount", clientList.size());

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		int countTodo_itmes = todo_itemsService.count();
		model.addAttribute("workspaceCount", countTodo_itmes);

		int countTweet = tweetService.count();
		model.addAttribute("tweetcount", countTweet);

		int countAdminNotice = adminService.count();
		model.addAttribute("adminListCount", countAdminNotice);

		List<One_to_oneMailDTO> one_to_onemailCount = one_to_oneMailService.selectMany(getName);
		model.addAttribute("one_to_oneMailCount", one_to_onemailCount.size());

		int rowNumber = personUsersNoticeService.count(getName);
		model.addAttribute("adminPersonNoticeCount", rowNumber);

		int countAdmin = inquiryService.count();
		model.addAttribute("inquiryListCount", countAdmin);

		return "login/workspaceLayout";
	}

	@PostMapping(value = "/clientDetail", params = "delete")
	public String postClientDetail(@ModelAttribute ClientDeleteForm form, Model model) {
		model.addAttribute("contents", "login/client::workspaceLayout_contents");
		int rowNumber = clientService.deleteOne(form.getId());
		if (rowNumber > 0) {
			System.out.println("delete成功");
		} else {
			System.out.println("delete失敗");
		}

		ClientSearchForm clientsearchform = new ClientSearchForm();
		model.addAttribute("clientSearchForm", clientsearchform);

		ClientDetailForm clientdetailform = new ClientDetailForm();
		model.addAttribute("clientDetailForm", clientdetailform);

		return getClient(clientsearchform, clientdetailform, form, model);
	}

	@PostMapping(value = "/clientDetail", params = "update")
	public String postClientDetailUpdate(@ModelAttribute @Validated(GroupOrder.class) ClientDetailForm form,
			BindingResult bindinResult, ClientSearchForm form2, Model model) {
		model.addAttribute("contents", "login/client::workspaceLayout_contents");

		if (bindinResult.hasErrors()) {
			int getId = form.getId();
			return getClientDetail(form, model, getId);
		}

		ClientDTO clientdto = new ClientDTO();
		clientdto.setId(form.getId());
		clientdto.setUser_name(form.getUser_name());
		clientdto.setTelephone(form.getTelephone());
		clientdto.setMailaddress(form.getMailaddress());

		int rowNumber = clientService.updateOne(clientdto);

		if (rowNumber > 0) {
			System.out.println("update成功");
		} else {
			System.out.println("update失敗");
		}

		ClientDeleteForm clientdeleteform = new ClientDeleteForm();
		model.addAttribute("clientdeleteform", clientdeleteform);
		return getClient(form2, form, clientdeleteform, model);

	}

	//ログアウト用メソッド
	@PostMapping("logout")
	public String postLogout() {

		//ログイン画面にリダイレクト
		return "redirect:/login";
	}

	//adminNotice一覧のCSV出力用処理
	@GetMapping("/adminNotice/csv")
	public ResponseEntity<byte[]> getAdminCsv(Model model) {

		System.out.println("adminCsv到達");

		adminService.adminCsvOut();

		byte[] bytes = null;

		try {
			//サーバーに保存されているadmdin.csvファイルをbyteで取得する
			bytes = adminService.getFile("admin.csv");

		} catch (Exception e) {
			e.printStackTrace();
		}
		//HTTPヘッダーの設定
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv;charset=UTF-8");
		header.setContentDispositionFormData("filename", "admin.csv");
		return new ResponseEntity<>(bytes, header, HttpStatus.OK);
	}

	//inquiry一覧のCSV出力用処理
	@GetMapping("/inquiry/csv")
	public ResponseEntity<byte[]> getInquiryCsv(Model model) {

		System.out.println("inquiryCsv到達");

		inquiryService.inquiryCsvOut();

		byte[] bytes = null;

		try {

			//サーバーに保存されているinquiry.csvファイルをbyteで取得する
			bytes = inquiryService.getFile("inquiry.csv");

		} catch (Exception e) {
			e.printStackTrace();
		}

		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv;charset=UTF-8");
		header.setContentDispositionFormData("filename", "inquiry.csv");

		return new ResponseEntity<>(bytes, header, HttpStatus.OK);
	}

	//ユーザー一覧のCSV出力用メソッド
	@GetMapping("usersList/csv")
	public ResponseEntity<byte[]> getUsersListCsv(Model model) {

		//ユーザーを管理者以外全件通して、CSVをサーバー保存する
		String user_id = "administratorMaruyama@yuuma";
		usersService.usersCsvOut(user_id);

		byte[] bytes = null;

		try {

			bytes = usersService.getFile("users.csv");

		} catch (Exception e) {
			e.printStackTrace();
		}

		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv; charset=UTF-8");
		header.setContentDispositionFormData("filename", "usersList.csv");

		return new ResponseEntity<>(bytes, header, HttpStatus.OK);

	}

	@GetMapping("tweet/csv")
	public ResponseEntity<byte[]> getTweetCsv(Model model) {

		//CSVをサーバーに保存する
		tweetService.tweetCsvOut();

		byte[] bytes = null;

		try {
			//サーバーに保存されているtweet.csvファイルをbyteで取得する
			bytes = tweetService.file("tweet.csv");

		} catch (Exception e) {
			e.printStackTrace();
		}

		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv; charset=UTF-8");
		header.setContentDispositionFormData("filename", "tweet.csv");

		return new ResponseEntity<>(bytes, header, HttpStatus.OK);
	}

	@GetMapping("personMemo/csv")
	public ResponseEntity<byte[]> getPersonMemoCsv(Model model) {

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();
		personMemoService.personMemoCsvOut(getName);

		byte[] bytes = null;

		try {

			bytes = personMemoService.file("personMemo.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}

		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv; charset=UTF-8");
		header.setContentDispositionFormData("filename", "personMemo.csv");

		return new ResponseEntity<>(bytes, header, HttpStatus.OK);
	}

	@GetMapping("/personUsersNotice/csv")
	public ResponseEntity<byte[]> getPersonUsersNoticeCsv(Model model) {

		System.out.println("personUsersNoticeCsv到達");
		personUsersNoticeService.personUsersNoticeCsvOut();

		byte[] bytes = null;

		try {

			bytes = personUsersNoticeService.getFile("personUsersNotice.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}

		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv; charset=UTF-8");
		header.setContentDispositionFormData("filename", "personUsersNotice.csv");

		return new ResponseEntity<>(bytes, header, HttpStatus.OK);
	}

	@GetMapping("/personUsersNoticeSending/csv")
	public ResponseEntity<byte[]> getPersonUsersNoticeSendingCsv(Model model) {

		System.out.println("personUsersNoticeCsv到達");

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		personUsersNoticeService.personUsersNoticeSendingCsvOut(getName);

		byte[] bytes = null;

		try {

			bytes = personUsersNoticeService.getFile("personUsersNoticeSending.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}

		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv; charset=UTF-8");
		header.setContentDispositionFormData("filename", "personUsersNoticeSending.csv");

		return new ResponseEntity<>(bytes, header, HttpStatus.OK);
	}

	@GetMapping("/one_to_oneMail/csv")
	public ResponseEntity<byte[]> getOne_to_oneMailCsv(@ModelAttribute One_to_oneMailForm form, Model model) {

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());
		String getName = auth2.getName();

		System.out.println("one_to_oneMailCsv到達");
		one_to_oneMailService.one_to_oneMailCsvOut(getName);

		byte[] bytes = null;

		try {

			bytes = one_to_oneMailService.file("one_to_oneMail.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}

		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv; charset=UTF-8");
		header.setContentDispositionFormData("filename", "one_to_oneMail.csv");

		return new ResponseEntity<>(bytes, header, HttpStatus.OK);
	}

	@GetMapping("/one_to_oneMailSending/csv")
	public ResponseEntity<byte[]> getOne_to_oneMailSendingCsv(@ModelAttribute One_to_oneMailForm form, Model model) {

		Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();

		System.out.println("auth" + auth2);
		System.out.println("auth.getName" + auth2.getName());

		String getName = auth2.getName();

		System.out.println("one_to_oneMailSendingCsv到達");
		one_to_oneMailService.one_to_oneMailSendingCsvOut(getName);

		byte[] bytes = null;

		try {

			bytes = one_to_oneMailService.file("one_to_oneMailSending.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}

		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "text/csv; charset=UTF-8");
		header.setContentDispositionFormData("filename", "one_to_oneMailSending.csv");

		return new ResponseEntity<>(bytes, header, HttpStatus.OK);
	}

	@GetMapping("/client/csv")
	public ResponseEntity<byte[]> getClientCsv(Model model) {
		System.out.println("clientCsv到達");
		clientService.clientCsvOut();

		byte[] bytes = null;

		try {

			bytes = clientService.file("client.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}

		HttpHeaders header = new HttpHeaders();

		header.add("Content-Type", "text/csv; charset=UTF-8");
		header.setContentDispositionFormData("filename", "clientInformation.csv");

		return new ResponseEntity<>(bytes, header, HttpStatus.OK);
	}

}

