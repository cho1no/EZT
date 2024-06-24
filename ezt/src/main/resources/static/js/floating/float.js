let stomp = null;
let sockJs = null;
let currentRoomNo = null;
// date format
function formatDate(date) {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 1을 더합니다.
  const day = String(date.getDate()).padStart(2, '0');
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');

  return `${year}/${month}/${day} ${hours}:${minutes}`;
}
// 초기화(최초상태)
function initializeWebSocket() {
  sockJs = new SockJS("/stomp/chat");
  stomp = Stomp.over(sockJs);

  stomp.connect({}, function () {
    console.log("WebSocket connected");
    
    if (currentRoomNo) {
      subscribeToRoom(currentRoomNo);
    } else {
      subscribeAllChat();
    }
  });
}
// 채팅방 목록 시
function subscribeAllChat(){
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
      stomp.unsubscribe('sub-'+(stomp.counter-1));
    }

    currentRoomNo = roomNo;
    stomp.subscribe(`/sub/chat/room/${roomNo}`, function (chat) {
      var ct = JSON.parse(chat.body);
      $(".floated-body").append(
        $(".floated-body").append(
          $(`<div class='cMine chatDiv'>
            <p><b>${ct.usersNo}</b><span class="chatTime float-end">${formatDate(new Date())}</span></p>
            <p>${ct.content}</p>
          </div>`)
      ));
      $('.floated-body').scrollTop($('.floated-body')[0].scrollHeight);
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
$(document).ready(function () {
  // 소켓 열기
  initializeWebSocket();
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
});
// Create Chat Rooms List Window
// 채팅방 리스트 그리기
function CCRLW() {
  $(".chat-footer").css("display", "none");
  $(".floatedTitle").text("채팅방");
  $(".floated-body").html('');
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
          </div>`).click(
            () => {
              DCH(e.chatRoomNo);
              subscribeToRoom(e.chatRoomNo);
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

// draw chat history
// 채팅 기록 그리기
function DCH(roomNo) {
  $(".floatedTitle").html('');
  $(".floatedTitle").append($(`<i class="fa-solid fa-chevron-left cpointer"></i>`).click(()=>{
    CCRLW();
    stomp.unsubscribe('sub-'+(stomp.counter-1));
  }));
  $(".floatedTitle").append($(`<span> 채팅</span>`));
  $(".chat-footer").css("display", "flex");
  $(".floated-body").html("");
  $.ajax({
    type: "get",
    url: "/getChats/" + roomNo,
    success: function (response) {
      $(response).each((i, e) => {
        let whoChat = e.usersNo == uno ? 'cMine' : 'cNotMine';
        let date = formatDate(new Date(e.chatTime));
        $(".floated-body").append(
          $(`<div class='${whoChat} chatDiv'>
            <p><b>${e.usersNo}</b><span class="chatTime float-end">${date}</span></p>
            <p>${e.content}</p>
          </div>`)
        );
      });
      $('.floated-body').scrollTop($('.floated-body')[0].scrollHeight);
    },
  });
}

// Send message
function sendMessage(){
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

// Send Event
$("#chat-send").on("click", function (e) {
  sendMessage();
});
function enterKeyDown(){
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
