let stomp = null;
$(".floating-nav li").each((i, e) => {
  $(e).click(
    () => (document.getElementById("floatedWindow").style.display = "flex")
  );
});
$("#chatButton").click(() => {
  CCRLW();
});

//Create Chat Rooms List Window
function CCRLW() {
  $(".floatedTitle").text("채팅방");
  $.ajax({
    type: "get",
    url: "/getRooms",
    success: function (response) {
      $(response).each((i, e) => {
        $(".floated-body").append(
          $("<p data-cno=" + e.chatRoomNo + ">" + e.title + "</p>").addClass('cpointer').click(
            () => {
              DCH(e.chatRoomNo);
              openSocket(e.chatRoomNo);
            }
          )
        );
      });
    },
    error: function () {
      $(".floated-body").append($("<p>대화 로딩 실패</p>"));
    },
  });
}

//draw chat history
function DCH(roomNo) {
  $(".floatedTitle").text("채팅");
  $(".chat-footer").css("display", "flex");
  $(".floated-body").html("");
  $.ajax({
    type: "get",
    url: "/getChats/" + roomNo,
    success: function (response) {
      $(response).each((i, e) => {
        $(".floated-body").append(
          $("<p><span>" + e.usersNo + "</span> : " + e.content + "</p>")
        );
      });
    },
  });
}
//Open Socket
function openSocket(roomNo) {
  let sockJs = new SockJS("/stomp/chat");
  //1. SockJS를 내부에 들고있는 stomp를 내어줌
  stomp = Stomp.over(sockJs);

  //2. connection이 맺어지면 실행
  stomp.connect({}, function () {
    //4. subscribe(path, callback)으로 메세지를 받을 수 있음
    stomp.subscribe("/sub/chat/room/" + roomNo, function (chat) {
      var contents = JSON.parse(chat.body);

      var writer = contents.writer;
      var str = "";

      if (writer === uno) {
        str = "<p><span>" + writer + "</span> : " + contents.content + "</p>";
        $(".floated-body").append(str);
      } else {
        str = "<p><span>" + writer + "</span> : " + contents.content + "</p>";
        $(".floated-body").append(str);
      }
    });

    //3. send(path, header, message)로 메세지를 보낼 수 있음
    stomp.send(
      "/pub/chat/enter",
      {},
      JSON.stringify({
        chatRoomNo: roomNo,
        writer: uno,
        usersNo: uno,
      })
    );
  });
  $("#chat-send").on("click", function (e) {
    var msg = document.getElementById("chat-input");
    stomp.send(
      "/pub/chat/message",
      {},
      JSON.stringify({
        chatRoomNo: roomNo,
        content: msg.value,
        writer: uno,
        usersNo: uno,
      })
    );
    msg.value = "";
  });
}

$("#closeFloatedWindow").click(() => {
  $(".floated-body").html("");
  $("#floatedWindow").css("display", "none");
});
