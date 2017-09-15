<!DOCTYPE html>
<html>
  <body>
  	<div style="text-align:center">
  		<%@ include file="YoutubeIndex.jsp" %>   	
		<a href="SearchVideo.jsp?searchKeyword=${param.searchKeyword}">Back to Search results</a>
	</div>
	
	<br/><br/>
	
	<jsp:useBean id="videoYoutube" class="edu.uwt.cloud.youtubeapi.SearchYoutube">
		<jsp:setProperty name="videoYoutube" property="videoId" value="${param.videoId}"/>
	</jsp:useBean>
	<c:set var="video" value="${videoYoutube.videoResult}"/>
	<c:set var="channel" value="${videoYoutube.channelResult}"/>
	
	<div id="video">
		 <p style="font-size:20px ">
		 	<img src="${channel.snippet.thumbnails['default'].url}" width="40" height="40"/> &nbsp;&nbsp;
		 	<b>${channel.snippet.title}</b>&nbsp;&nbsp;
		 	Subscribers : ${channel.statistics.subscriberCount} &nbsp;|&nbsp; No of videos : ${channel.statistics.videoCount}
		 </p> 
		 <br/>
		 <i>${channel.snippet.description}</i>
				
		 <div style="text-align:center">
  			<p style="font-size:25px "><b> ${video.snippet.title}</b></p>
			Description : <i>${video.snippet.description}</i><br/>
			
		  	<div id="player">  </div>		
		  	
  		</div>		
	</div>
	
    <!-- 1. The <iframe> (and video player) will replace this <div> tag. -->
     
	 <script>
      // 2. This code loads the IFrame Player API code asynchronously.
      var tag = document.createElement('script');
     	var videoId='<c:out value="${param.videoId}"/>';

      tag.src = "https://www.youtube.com/iframe_api";
      var firstScriptTag = document.getElementsByTagName('script')[0];
      firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

      // 3. This function creates an <iframe> (and YouTube player)
      //    after the API code downloads.
      var player;
      function onYouTubeIframeAPIReady() {
        player = new YT.Player('player', {
          height: '390',
          width: '640',
          videoId: videoId,
          events: {
            'onStateChange': onPlayerStateChange
          }
        });
        
      }

      // 4. The API will call this function when the video player is ready.
      function onPlayerReady(event) {
        event.target.playVideo();
      }

      // 5. The API calls this function when the player's state changes.
      //    The function indicates that when playing a video (state=1),
      //    the player should play for six seconds and then stop.
      var done = false;
      function onPlayerStateChange(event) {
        if (event.data == YT.PlayerState.PLAYING && !done) {
          setTimeout(stopVideo, 6000);
          done = true;
        }
      }
      function stopVideo() {
        player.stopVideo();
      }
    </script>
  </body>
</html>