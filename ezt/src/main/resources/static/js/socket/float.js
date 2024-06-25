/**
 * float.js
 * 우측 하단 토글 매뉴
 */

let currentRoomNo = null;

// 채팅방 목록 시
function subscribeAllChat() {
  // stomp.subscribe(`/sub/chat/room`, function(chat) {
  //   let ct = JSON.parse(chat.body);
  //   let isExist = false;
  //   $('.chatRoomDiv').each((i,e) => {
  //     console.log($(e).data('cno'));
  //     if ($(e).data('cno') == ct.chatRoomNo) isExist = true;
  //   });
  //   if (isExist){
  //     $(`.chatRoomDiv[data-cno=${ct.chatRoomNo}]`).find('#currChat').text(ct.content);
  //   }
  // });
}
// 채팅방 입장 시
function subscribeToRoom(roomNo) {
  if (currentRoomNo !== roomNo) {
    if (currentRoomNo) {
      stomp.unsubscribe("sub-" + (stomp.counter - 1));
    }

    currentRoomNo = roomNo;
    stomp.subscribe(`/sub/chat/room/${roomNo}`, function (chat) {
      var ct = JSON.parse(chat.body);
      $(".floated-body").append($(drawChatDetail(ct)));
      $(".floated-body").scrollTop($(".floated-body")[0].scrollHeight);
    });

    stomp.send(
      "/pub/chat/enter",
      {},
      JSON.stringify({
        chatRoomNo: roomNo,
        writer: uno,
        usersNo: uno,
      })
    );
  }
}
// 문서 로드 완료

// Create Chat Rooms List Window
// 채팅방 리스트 그리기
function CCRLW() {
  $(".chat-footer").css("display", "none");
  $(".floatedTitle").text("채팅방");
  $(".floated-body").html("");
  $.ajax({
    type: "get",
    url: "/getRooms",
    success: function (response) {
      $(response).each((i, e) => {
        let date = formatDate(new Date(e.updateDt));
        $(".floated-body").append(
          $(`<div class="chatRoomDiv cpointer" data-cno='${e.chatRoomNo}'>
            <p><b>${e.title}</b><span class="chatTime float-end">${date}</span></p>
            <p><span id="currChat">${e.content}</span><span class="float-end">1</span></p>
            </div>`).click(() => {
            chatRoomClickEvent(e);
          })
        );
      });
    },
    error: function () {
      $(".floated-body").append($("<p>대화 로딩 실패</p>"));
    },
  });
}
// 채팅방 클릭 이벤트
function chatRoomClickEvent(obj) {
  DCH(obj.chatRoomNo);
  subscribeToRoom(obj.chatRoomNo);
  $("#chat-input").focus();
}
// draw chat history
// 채팅 기록 그리기
function DCH(roomNo) {
  $(".floatedTitle").html("");
  $(".floatedTitle").append(
    $(`<i class="fa-solid fa-chevron-left cpointer"></i>`).click(() => {
      CCRLW();
      stomp.unsubscribe("sub-" + (stomp.counter - 1));
    })
  );
  $(".floatedTitle").append($(`<span> 채팅</span>`));
  $(".chat-footer").css("display", "flex");
  $(".floated-body").html("");
  $.ajax({
    type: "get",
    url: "/getChats/" + roomNo,
    success: function (response) {
      $(response).each((i, e) => {
        $(".floated-body").append($(drawChatDetail(e)));
      });
      $(".floated-body").scrollTop($(".floated-body")[0].scrollHeight);
    },
  });
}

function drawChatDetail(obj) {
  let whoChat = obj.usersNo == uno ? "cMine" : "cNotMine";
  let date = obj.chatTime == null ? new Date() : new Date(obj.chatTime);
  date = formatDate(date);
  return `<div class='${whoChat} chatDiv'>
    <p><b>${obj.usersNo}</b><span class="chatTime float-end">${date}</span></p>
    <p>${obj.content}</p>
    </div>`;
}

// Send message
function sendMessage() {
  var msg = document.getElementById("chat-input");
  stomp.send(
    "/pub/chat/message",
    {},
    JSON.stringify({
      chatRoomNo: currentRoomNo,
      content: msg.value,
      writer: uno,
      usersNo: uno,
    })
  );
  msg.value = "";
}
// 메뉴 클릭마다 창띄우기
$(".floating-nav li").each((i, e) => {
  $(e).click(
    () => (document.getElementById("floatedWindow").style.display = "flex")
  );
});
// 채팅버튼 클릭 시 채팅방 그리기
$("#chatButton").click(() => {
  CCRLW();
});

// Send Event
$("#chat-send").on("click", function (e) {
  sendMessage();
});
function enterKeyDown() {
  if (window.event.keyCode == 13) {
    sendMessage();
  }
}
// 닫기 버튼 클릭
$("#closeFloatedWindow").click(() => {
  $(".floated-body").html("");
  $("#floatedWindow").css("display", "none");
});

// 연결종료
window.onbeforeunload = function () {
  if (stomp !== null) {
    stomp.disconnect();
  }
};
