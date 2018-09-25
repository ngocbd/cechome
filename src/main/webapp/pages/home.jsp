<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="/elements/header.jsp" />
<jsp:useBean id="myDate" class="java.util.Date"/>

<pre>
<aside>
	*** Giới thiệu về CEC habogay
	<image src = "https://storage.googleapis.com/crazy-english-community.appspot.com/images/about.jpg" alt="About CEC"/>
	CEC is a nongovernmental and nonprofit organization and is activated at October 30th, 2014 that trains real English by using Crazy English method, orients and introduces jobs for Vietnamese student generation.

	Vietnamese Youth has minimal opportunity to make income and the reasons include: Low professional skill and especially is English skill. The ratio of the unemployed of Vietnamese Youth is high. The dependence and respect themselves are decreasing.

	CEC is focusing on the Youth who needs a skill training. It is very easy for them to attend and is totally free. We train students at every age and in all places at all times.
	<a href="/about" target = "_blank">Readmore</a>
</aside>

<aside>*** Các hoạt động chính của CEC</aside>
<aside>*** Các cách tham gia cùng CEC</aside>
<aside>
	*** Video
	<div class="fb-video" data-href="https://www.facebook.com/100012576995887/videos/519733341789228" data-width="500" data-show-text="false">
  	</div>
</aside>
<aside>

	*** Ai la trieu phu ***
	* WHO WANTS TO BE A MILLIONAIRE?
	THE PRIZE STRUCTURE
	The first prize: 1000 CEC 
	(1000 CEC can be exchanged into phone card worth 200.000 VND)
	The second prize: 500 CEC 
	(500 CEC can be exchanged into phone card worth 100.000 VND)
	The third prize: 250 CEC 
	(250 CEC can be exchanged into phone card worth 50.000 VND)
	You can also accumulate your points to exchange into another gift that' s more valued.
	
	HERE ARE THE RULES
	Everyweek, a model video with textcrip will be given to you. Your task is to creat your own video in the most interesting way, based on the orginial content.
	The winner in that week is the person who gets the highest point that corresponds with the total number of likes, emotions, comments and shares.
	<a href="https://www.facebook.com/groups/cec.edu.vn/permalink/2039738106299235/" target = "_blank" >Join</a>
	
	* GIVE AND TAKE
	
	THE PRIZE:
	The first prize of the day: 50 CEC 
	(50 CEC can be exchanged into phone card worth 10.000 VND).
	The first prize of the week: 500 CEC 
	(500 CEC can be exchanged into phone card worth 100.000 VND).
	CEC are exchangeable to phone cards. You can also accumulate your points to exchange into a gift that' s more valued.
	
	HERE ARE THE RULES
	You will write an article about what you know about English or a life lesson you enjoy the most.
	The winner in that day is the one who has the most likes, shares and comments.
	The winner of the week is the one who has the highest total interaction of the week.
	<a href="https://www.facebook.com/groups/cec.edu.vn/permalink/2039738106299235/" target = "_blank" >Join</a>
	
	*video theo cuoc thi : ailatrieuphu
	<c:forEach items="${altpPosts}" var="altpPost"> 
	  <article>
	  	<section> long datetime type ${altpPost.createdDate}</section>
	    <section>${altpPost.content}</section>
	    <section><div class="fb-video" data-href="${altpPost.picture}" data-width="500" data-show-text="false"></div></section>
	    <section><a href = "https://www.facebook.com/groups/cec.edu.vn/permalink/${fn:replace(altpPost.id, '1784461175160264_', '')}" target = "_blank">${fn:replace(altpPost.id, '1784461175160264_', '')}</a></section>
	   	<section><a rel="author" href="https://cec.net.vn/m/${altpPost.posterId}">${memberIds[altpPost.posterId].name}</a></section>	    
	  </article>
	</c:forEach>
	
	
	*** Hành trình chinh phục tiếng anh
	<c:forEach items="${day90Posts}" var="day90Post"> 
	  <article>
	  	<section> long datetime type ${day90Post.createdDate}</section>
	    <section>${day90Post.content}</section>
	    <section><div class="fb-video" data-href="${day90Post.picture}" data-width="500" data-show-text="false"></div></section>
	    <section><a href = "https://www.facebook.com/groups/cec.edu.vn/permalink/${fn:replace(day90Post.id, '1784461175160264_', '')}" target = "_blank">${fn:replace(day90Post.id, '1784461175160264_', '')}</a></section>
	   	<section><a rel="author" href="https://cec.net.vn/m/${day90Post.posterId}">${day90Ids[day90Post.posterId].name}</a></section>	    
	  </article>
	</c:forEach>
	
	*** Săn Tây Events 
	<c:forEach items="${santayPosts}" var="santayPost"> 
	  <article>
	  	<section> long datetime type ${santayPost.createdDate}</section>
	    <section>${santayPost.content}</section>
	    <section><div class="fb-video" data-href="${santayPost.picture}" data-width="500" data-show-text="false"></div></section>
	    <section><a href = "https://www.facebook.com/groups/cec.edu.vn/permalink/${fn:replace(santayPost.id, '1784461175160264_', '')}" target = "_blank">${fn:replace(santayPost.id, '1784461175160264_', '')}</a></section>
	   	<section><a rel="author" href="https://cec.net.vn/m/${santayPost.posterId}">${sanTayIds[santayPost.posterId].name}</a></section>	    
	  </article>
	</c:forEach>
	
	*** LiveStream Videos 
	<c:forEach items="${livestreamPosts}" var="livestreamPost"> 
	  <article>
	  	<section> long datetime type ${livestreamPost.createdDate}</section>
	    <section>${livestreamPost.content}</section>
	    <section><div class="fb-video" data-href="${livestreamPost.picture}" data-width="500" data-show-text="false"></div></section>
	    <section><a href = "https://www.facebook.com/groups/cec.edu.vn/permalink/${fn:replace(livestreamPost.id, '1784461175160264_', '')}" target = "_blank">${fn:replace(livestreamPost.id, '1784461175160264_', '')}</a></section>
	   	<section><a rel="author" href="https://cec.net.vn/m/${livestreamPost.posterId}">${livestreamIds[livestreamPost.posterId].name}</a></section>	    
	  </article>
	</c:forEach>
	
	*** Danh sách bài cần chữa
	<article>          Người yêu cầu       Bài cần chữa           Thời gian gửi bài(... ago)             Người chữa      Thời gian nhận bài(... ago)</article>
	<c:forEach items="${requestReviewPosts}" var="requestReviewPost"> 
	  <article>
	  	<img alt = "requester review" src="//graph.facebook.com/${requestReviewPost.requesterAccountId}/picture?type=square" />    <a rel="post detail" href="https://www.facebook.com/groups/cec.edu.vn/permalink/${fn:replace(requestReviewPost.postid, '1784461175160264_', '')}"  target="_blank">${fn:replace(requestReviewPost.postid, '1784461175160264_', '')}</a>           <time>${requestReviewPost.createdDate}</time>                       <c:choose> <c:when test="${requestReviewPost.editorAccountId ==null }"><!-- chỗ này Tít chế 1 cái ảnh để tạm chữ là "No Editor". C cũng chưa nghĩ ra ảnh này nên có nội dung như nào, nhưng nghĩa của nó là chưa có editor nào nhận sửa bài này. C đang để tạm ảnh profile fb của c. --><img alt = "no editor review" src="//graph.facebook.com/100009525166032/picture?type=square" /></c:when> <c:otherwise><img alt = "editor review" src="//graph.facebook.com/${requestReviewPost.editorAccountId}/picture?type=square" /></c:otherwise>	</c:choose>                 <time>${requestReviewPost.reviewDate}</time> 
	  </article>
	</c:forEach>
	
	
	*** Danh sách bài đã chữa
	<article>          Người yêu cầu       Bài đã chữa           Thời gian gửi bài(... ago)             Người chữa      Thời gian nhận bài(... ago)</article>
	<c:forEach items="${reviewDonePosts}" var="reviewDonePost"> 
	  <article>
	  	<img alt = "requester review" src="//graph.facebook.com/${reviewDonePost.requesterAccountId}/picture?type=square" />    <a rel="post detail" href="https://www.facebook.com/groups/cec.edu.vn/permalink/${fn:replace(reviewDonePost.reviewPostId, '1784461175160264_', '')}" target="_blank">${fn:replace(reviewDonePost.reviewPostId, '1784461175160264_', '')}</a>           <time>${reviewDonePost.createdDate}</time>                       <c:choose> <c:when test="${reviewDonePost.editorAccountId ==null }"><!-- chỗ này Tít chế 1 cái ảnh để tạm chữ là "No Editor". C cũng chưa nghĩ ra ảnh này nên có nội dung như nào, nhưng nghĩa của nó là chưa có editor nào nhận sửa bài này. C đang để tạm ảnh profile fb của c. --><img alt = "no editor review" src="//graph.facebook.com/100009525166032/picture?type=square" /></c:when> <c:otherwise><img alt = "editor review" src="//graph.facebook.com/${reviewDonePost.editorAccountId}/picture?type=square" /></c:otherwise>	</c:choose>                 <time>${reviewDonePost.reviewDate}</time> 
	  </article>
	</c:forEach>
	
	
	*** Member List có nhiều tiền nhất
	<c:forEach items="${richMembers}" var="richMember"> 
	  <article>
	  	<img alt = "requester review" src="//graph.facebook.com/${richMember.id}/picture?type=square" />      <c:choose> <c:when test="${richMember.fbId ==null }">${richMember.name}</c:when><c:otherwise><a rel="post detail" href="https://cec.net.vn/m/${richMember.fbId}">${richMember.name}</a> <a class="fab fa-facebook" style="font-size:48px;" href="//facebook.com/profile.php?id=${richMember.fbId}" target = "_blank"></a><a class="fab fa-facebook-messenger" style="font-size:60px;color:blue;" href="//m.me/${richMember.fbId}" target = "_blank"></a></c:otherwise></c:choose>      
	  </article>
	</c:forEach>
	
	*** Member List có nhiều đóng góp nhất (Dedication)
	richMembers
	*** Member List có tương tác nhiều nhất 
	*** Member List vừa thắng các giải 
	
</aside>
</pre>
<jsp:include page="/elements/footer.jsp" />
<!-- 
1886673191605728
1893183020954745
1896182430654804 
1896325267307187
1900714246868289

<c:choose> <c:when test="${richMember.fbId ==null }">
			<a rel="post detail" href="https://user.cec.net.vn/account?fbid=null&accid=${richMember.id}">${richMember.name}</a>
		</c:when>
		<c:otherwise>
			<a rel="post detail" href="https://cec.net.vn/m/${richMember.fbId}">${richMember.name}</a> 
		</c:otherwise>
</c:choose>


						

# Giới thiệu về cộng đồng 
# Các hoạt động chính của CEC
# Các cách tham gia cùng CEC 
#Video  -> promote https://www.facebook.com/groups/cec.edu.vn/permalink/2117397505199961/?__xts__[0]=68.ARAlB_XmZpRr5QP56t-nc45hGBEhjHZe_GB9UWdCTOksaO1TTaRx4h032lT7oBMGqHWZzE-GFGxsamp2_QT_SeuGoY_BjkmbLFqb4e3ZB6twbJAbcq-dtaKucKkbFK_PxDVkQiFQna45&__tn__=-R
# video theo cuoc thi : ailatrieuphu

#Video theo seri vi du noi tiếng anh trong 90ngay ( cai nay goi la hanh trinh chinh phuc tieng anh )
# theo Event ( san tay o ho tay ) , Lịch Event sắp tới - có số điện thoại liên hệ với người tổ chức

#LiveStream - Đã diễn ra (MIKE , mrNam , Em Trang IPA ... )  , Đang - các livestream đang diễn ra,  lịch live steam trong 7 ngày tới
#CHữa bài 
# Yêu câu chữa bài ( là các video cần được chữa nhưng chưa có ai chữa - mất tiền )
# Các topic học zoom với người nước ngoài 1 on 1 , 

Member list theo các tiêu chí ( Nhiều tiền nhất , nhiều đóng góp nhất , Tương tác nhiều nhất , Vừa thắng các giải) 
-->