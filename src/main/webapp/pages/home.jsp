<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/elements/header.jsp" />


<aside>
	*** Giới thiệu về CEC
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
			  <section>${altpPost.createdDate}</section>
			    <section>${altpPost.content}</section>
			    <section><div class="fb-video" data-href="${altpPost.picture}" data-width="500" data-show-text="false"></section>
			    <section>${altpPost.permalink}</section>
			   	<a rel="author" href="https://cec.net.vn/m/${altpPost.posterId}">${memberIds[altpPost.posterId].name}</a>	    
			  </article>
			</c:forEach>
	
</aside>
<jsp:include page="/elements/footer.jsp" />
<!-- 
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

Member list theo các tiêu chí ( Nhiều tiền nhất , nhiều đóng góp nhất , Tương tác nhiều nhất , Vừa thắng các giải ) 
-->