package net.cec.mesenger;

public class format {

	public format()
	{
		/*
		 try 
		 {
			 JSONObject jsonObject = new JSONObject(buffer.toString()); 
			 JSONArrayjsonArray = new JSONArray(jsonObject.get("entry").toString()); 
			 JSONObject jsonObject2 = new JSONObject(jsonArray.get(0).toString()); 
			 JSONArray jsonArray2 = new JSONArray(jsonObject2.get("messaging").toString());
			 JSONObject jsonObject3 = new JSONObject(jsonArray2.get(0).toString());
			 JSONObject jsonObject4 = null; 
			 String mes = ""; 
			 if(jsonObject3.get("message") != null) 
			 { 
				 jsonObject4 = new JSONObject(jsonObject3.get("sender").toString()); 
				 String messengerId = jsonObject4.get("id").toString(); 
				 if(messengerId.equals("1529642937274220")) return; 
				 Account account = this.getAccountByMessengerId(messengerId);
				 JSONObject jsonObject5 = new JSONObject(jsonObject3.get("message").toString()); 
				 String text = jsonObject5.get("text").toString().trim().replaceAll("[ ]+", " "); 
				 mes = "Xin chào tôi có thể giúp gì cho bạn."; 
				 if (text == "test") 
				 { 
					 mes = "Hello";
				 } 
				 //verify facebook id of the account. 
				 else if (text.startsWith("#verify")) 
				 {
					 //code: #verify [facebook id number] 
					 String fbId = this.getNumberFromString(text); 
					 account.setFbId(fbId);
					 log.warning("Id of the Facebook acc by verify: " + fbId);
					 log.warning("get fbId from acc: "+account.getFbId());
					 ofy().save().entities(account).now(); 
					 mes = "Facebook Id Number của bạn đã được verify"; 
				 } 
				 //update content of the Account's content. 
				 else if(text.startsWith("#update ")) 
				 { 
					 //code: #update https://www.facebook.com/groups/cec.edu.vn/permalink/[id of post] 
					 Matcher matcher = Pattern.compile("(\\d+)").matcher(text); 
					 matcher.find(); 
					 String postId = matcher.group(1); 
					 log.warning("The PostId: " + postId); 
					 String memberPostId = "1784461175160264_"+postId; 
					 MemberPost memberPostFromKey = ofy().load().type(MemberPost.class).id(memberPostId).now();
					 log.warning("The MemberPost Id: " + memberPostFromKey.getId());
					 if(memberPostFromKey!=null) 
					 { 
						 String strId = memberPostFromKey.getId();
						 String posterId = memberPostFromKey.getPosterId(); 
						 Queue queue = QueueFactory.getDefaultQueue();
						 queue.add(TaskOptions.Builder.withUrl("/task/update/post").param("id",strId).param("posterid", posterId) );
						 mes = "Chúng tôi sẽ cập nhật bài viết của bạn trong vài giây tới"; 
					 }
			 	} 
				//The member wants to review the member's lession. 
				else if(text.startsWith("#yccb ")) 
				{ 
					//code: #yccb https://www.facebook.com/groups/cec.edu.vn/permalink/[id of post] 
					try 
					{
						Matcher matcher = Pattern.compile("(\\d+)").matcher(text); 
						matcher.find();
					
						String postId = matcher.group(1); 
						log.warning("The PostId in yccb: " + postId); 
						String memberPostId = "1784461175160264_"+postId; 
						MemberPost memberPostFromKey = null; 
						Key<MemberPost> memberPostkey = Key.create(MemberPost.class, memberPostId); 
						try 
						{ 
							 memberPostFromKey = ofy().load().key(memberPostkey).safe(); 
						}
						catch(NotFoundException nfe) 
						{
							String links = "https://www.facebook.com/groups/cec.edu.vn/permalink/"+postId+"/\n";
							 Jsoup.connect("http://httpsns.appspot.com/queue?name=cecurl")
							 .ignoreContentType(true) .timeout(60 1000) .method(Method.POST)
							 .ignoreHttpErrors(true) .requestBody(links) .execute();
							 resp.getWriter().println(links); 
							 log.warning("The link: " + links); 
							 mes = "Chúng tôi chưa có bài viết này của bạn. Vui lòng đợi trong giây lát rồi gửi lại lệnh";
						 
						 } 
						log.warning("The MemberPost Id: " + memberPostFromKey.getId());
		 
						 if(memberPostFromKey!=null) 
						 { 
						 	Key<RequestReview> key = Key.create(RequestReview.class, memberPostFromKey.getId()); 
						 	RequestReview requestReview = ofy().load().key(key).now(); 
						 	if(requestReview==null) 
						 	{
						 		requestReview = new RequestReview();
						 		requestReview.setPostid(memberPostFromKey.getId());
						 		if(account.getMessengerId()!=null) 
						 		{
						 			requestReview.setRequesterId(account.getMessengerId()); 
						 		}
						 
						 		requestReview.setCreatedDate(Calendar.getInstance().getTime().getTime());
						 		requestReview.setStatus(0); requestReview.setPrice(10);
						 		ofy().save().entities(requestReview); 
						 		mes = "Chúng tôi đã nhận được yêu cầu sửa bài của bạn. Chúng tôi sẽ cập nhật bài viết của bạn trong vài giây" ; 
						 	} 
						 	else 
						 	{ 
						 		log.warning("habogay da tung di dao qua day"); 
						 		mes = "Yêu cầu chữa bài của bạn đang được xử lý"; 
						 		Query<Editor> q = ofy().load().type(Editor.class); 
						 		String reqMes = "Bài yêu cầu được chữa. Nếu bạn chữa bài này,hãy gửi lại một mã với nội dung: #suabai https://www.facebook.com/groups/cec.edu.vn/permalink/" + postId; 
						 		log.warning("size of editor: "+q.list().size()); 
						 		String messIdList = "List of messengerId: \n"; 
						 		for(int i = 0;i<q.list().size();i++) 
						 		{
						 			log.warning("editorId: "+q.list().get(i).getId()); 
						 			long accId = Long.parseLong(q.list().get(i).getId()); 
						 			Key<Account> accKey = Key.create(Account.class, accId); 
						 			Account accountFromEditor = ofy().load().key(accKey).now();
						 			log.warning("messengerId: "+accountFromEditor.getMessengerId());
						 			if(accountFromEditor.getMessengerId()!=null) 
						 			{ 
						 				messIdList += "\n"+accountFromEditor.getMessengerId();
						 				this.sendMessenge(accountFromEditor.getMessengerId(), reqMes); 
						 			}
		 
		 						} log.warning(messIdList); 
	 						}
		 
		 			}
		 
			 } 
			 catch (Exception e) 
			 { 
			 	// TODO: handle exception
			 	log.warning("Error: "+e.getMessage()+"\n"+e.getStackTrace()); throw e; 
			 } 
		 }
		 //The member wants to get new lession 
		 else if(text.startsWith("#ycbm ")) 
		 {
		 	//code: #ycbm https://www.facebook.com/groups/cec.edu.vn/permalink/[id of the post] 
		 	Matcher matcher = Pattern.compile("(\\d+)").matcher(text);
		 	matcher.find(); 
		 	String postId = matcher.group(1); 
		 	log.warning("The PostId: " + postId); 
		 	String memberPostId = "1784461175160264_"+postId; 
		 	Key<MemberPost> key = Key.create(MemberPost.class, memberPostId); 
		 	MemberPost memberPost = ofy().load().key(key).now(); 
		 	if(memberPost!=null) 
		 	{ 
		 		String str = (memberPost.getContent()).toLowerCase(); 
		 		Matcher matcherLesson = Pattern.compile("#lesson(\\d+)cec").matcher(str); 
		 		if (matcherLesson.find()) 
		 		{
		 			//send the new lesson to the student mes = "Bài luyện tiếp theo của bạn sẽ được gửi tới email của bạn"; 
		 		} 
		 		else 
		 		{ 
		 			mes = "Bạn gửi link bài luyện đã học không đúng."; 
		 		} 
		 	} 
		 	else 
		 	{ 
		 		String links = "https://www.facebook.com/groups/cec.edu.vn/permalink/"+postId+"/\n";
		 		Jsoup.connect("http://httpsns.appspot.com/queue?name=cecurl")
		 		.ignoreContentType(true) 
		 		.timeout(60 * 1000) 
		 		.method(Method.POST)
		 		.ignoreHttpErrors(true) 
		 		.requestBody(links) 
		 		.execute();
		 		resp.getWriter().println(links); 
		 		log.warning("The link: " + links); 
		 		mes = "Bài luyện tiếp theo của bạn sẽ được gửi tới email của bạn"; 
		 	}
		 
		 
		 } //the admin review for a member's lesson else
		 if(text.startsWith("#suabai ")) { //code: #subai
		 https://www.facebook.com/groups/cec.edu.vn/permalink/[id of the post] String
		 postId = this.getNumberFromString(text); // String memberPostId =
		 "1784461175160264_"+postId; mes =
		 "Bạn có 24 giờ để hoàn thành phần chữa bài này. Sau khi hoàn thành, hãy gửi mã với nội dung #sbtc https://www.facebook.com/groups/cec.edu.vn/permalink/"
		 +postId; log.warning("Start to get account name. MessengerId: "+messengerId);
		 String reqMes = account.getName()
		 +" đã nhận sửa bài https://www.facebook.com/groups/cec.edu.vn/permalink/"
		 +postId; this.sendAllMessageToEditors(reqMes); } 
		 //The admin finish the reviewing the member's lesson 
		 else if(text.startsWith("#sbtc ")) 
		 { 
		 	//code:#sbtc https://www.facebook.com/groups/cec.edu.vn/permalink/[id of the post]/
		 	Matcher matcher = Pattern.compile("(\\d+)").matcher(text); 
		 	matcher.find();
		 	String postId = matcher.group(1); 
		 	log.warning("The PostId: " + postId);
		 	String memberPostId = "1784461175160264_"+postId;
		 
		 	String accessToken = "EAAS2fFjpbzABAMwwxGgQczR3g4AlYoq1S3vKqZCgvqKvOUWswTavVtw7jkfPeA02NV9KNMn77ZAtj1t4ZBR1x2LLxUSbbc7J2Kjdw8dGFBMnnkGLRq1Hg4Xjx6PmHDvpsDZAeLpHBGI8rjzIg4iqZBDqWZABWdqhG0S2kQIqVlRAZDZD";
		 	FacebookClient fbClient = new DefaultFacebookClient(accessToken, Version.LATEST);
		 
		 	Post post = fbClient .fetchObject( postId, Post.class, Parameter.with("fields", "message, created_time"));
		 
			if(post != null) 
			{ 
				Key<MemberPost> key = Key.create(MemberPost.class, memberPostId); 
				MemberPost memberPost = ofy().load().key(key).now(); 
				String editorId = ""; 
				if(memberPost!=null) 
				{ 
					editorId = memberPost.getId(); 
				} 
				long reviewDate = post.getCreatedTime().getTime(); 
				String content = post.getMessage(); 
				List<String> listUrl = this.extractUrls(content); 
				for(int i=0;i<listUrl.size();i++) 
				{ 
					String urlPost = this.urlRedirect(listUrl.get(i));
			 		if(urlPost.startsWith("https://www.facebook.com/groups/cec.edu.vn/permalink/")) 
			 		{ 
			 			String postIdFromUrl = this.getNumberFromString(urlPost);
			 			Key<RequestReview> requestReviewKey = Key.create(RequestReview.class, post.getId()); 
			 			RequestReview requestReview = ofy().load().key(requestReviewKey).now(); 
			 			if(requestReview!=null) 
			 			{ 
			 				requestReview.setEditorId(editorId);
			 				requestReview.setReviewDate(reviewDate); 
			 				requestReview.setStatus(2);
		 					ofy().save().entities(requestReview); 
		 				} 
		 			} 
		 		} 
		 	} 
		 	
		 
		 			
			 this.sendMessenge(jsonObject4.get("id").toString(),mes); } catch (Exception
			 e) { log.warning(e.getMessage()+e.getStackTrace()); }
		 */
	}
}
