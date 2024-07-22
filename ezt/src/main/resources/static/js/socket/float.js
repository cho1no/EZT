/**
 * float.js
 * 우측 하단 토글 매뉴
 */

let currentRoomNo = null;

$(document).ready(function () {
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
          writer: uvo.usersName,
          usersNo: uvo.usersNo,
        })
      );
    }
  }

  // Create Chat Rooms List Window
  // 채팅방 리스트 그리기
  function CCRLW() {
    $(".chat-footer").css("display", "none");
    $(".floatedTitle").text("채팅방");
    $(".floated-body").html("");
    $.ajax({
      type: "get",
      url: "/cht/getRooms",
      success: function (response) {
        $(response).each((i, e) => {
          let date = formatDate(new Date(e.updateDt));
          $(".floated-body").append(
            $(`<div class="chatRoomDiv cpointer" data-cno='${e.chatRoomNo}'>
            <p><b>${e.title}</b><span class="chatTime float-end">${date}</span></p>
            <p id="currC"><span id="currChat">${e.content}</span></p>
            </div>`).click(() => {
              chatRoomClickEvent(e);
            })
          );
        });
      },
      error: function () {
        $(".floated-body").append($("<p>로그인 후 이용해주세요</p>"));
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
      url: "/cht/getChats/" + roomNo,
      success: function (response) {
        $(response).each((i, e) => {
          $(".floated-body").append($(drawChatDetail(e)));
        });
        $(".floated-body").scrollTop($(".floated-body")[0].scrollHeight);
      },
    });
  }

  function drawChatDetail(obj) {
    let whoChat = obj.usersNo == uvo.usersNo ? "cMine" : "cNotMine";
    let date = obj.chatTime == null ? new Date() : new Date(obj.chatTime);
    date = formatDate(date);
    return `<div class='${whoChat} chatDiv'>
    <p><b>${obj.writer}</b><span class="chatTime float-end">${date}</span></p>
    <p>${obj.content}</p>
    </div>`;
  }

  // 채팅버튼 클릭 시 채팅방 그리기
  $("#chatButton").click(() => {
    if (!$("#chatButton").hasClass("cshow")) {
      CCRLW();
      document.getElementById("floatedWindow").style.display = "flex";
      $("#chat-icon").removeClass("fa-comment-dots");
      $("#chat-icon").removeClass("fa");
      $("#chat-icon").addClass("fa-solid");
      $("#chat-icon").addClass("fa-x");
    } else {
      closeChat();
      $("#chat-icon").removeClass("fa-solid");
      $("#chat-icon").removeClass("fa-x");
      $("#chat-icon").addClass("fa-comment-dots");
      $("#chat-icon").addClass("fa");
    }
    $("#chatButton").toggleClass("cshow");
  });

  // Send Event
  $("#chat-send").on("click", function (e) {
    sendMessage();
  });
  // 닫기 버튼 클릭
  function closeChat() {
    $(".floated-body").html("");
    $("#floatedWindow").css("display", "none");
  }

  // 연결종료
  window.onbeforeunload = function () {
    if (stomp !== null) {
      stomp.disconnect();
    }
  };
});

function enterKeyDown() {
  if (window.event.keyCode == 13) {
    sendMessage();
  }
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
      writer: uvo.usersName,
      usersNo: uvo.usersNo,
    })
  );
  msg.value = "";
}

function createChatRoom(usersNo = 0) {
  send_data = { usersNos: [usersNo, uvo.usersNo] };
  console.log(usersNo);
  $.ajax({
    type: "post",
    url: "/cht/makeRoom",
    contentType: "application/json",
    data: JSON.stringify(send_data),
    success: function (response) {
      alert("채팅방 생성에 성공했습니다. \n우측 하단을 확인해주세요.");
    },
    error: function () {
      alert("채팅방 생성에 실패했습니다.");
    },
  });
}
