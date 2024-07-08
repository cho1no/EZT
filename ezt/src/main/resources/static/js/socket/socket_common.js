/**
 * socket_common.js
 * sockt default settings
 */

let stomp = null;
let sockJs = null;
let uvo = "";
function getMyInfo() {}
// date format
function formatDate(date) {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0"); // 월은 0부터 시작하므로 1을 더합니다.
  const day = String(date.getDate()).padStart(2, "0");
  const hours = String(date.getHours()).padStart(2, "0");
  const minutes = String(date.getMinutes()).padStart(2, "0");

  return `${year}/${month}/${day} ${hours}:${minutes}`;
}

// str slice to ...
function strSlice(str) {
  let length = 15; // 표시할 글자수 기준
  if (str.length > length) {
    str = str.substr(0, length - 2) + "...";
  }
  return str;
}
// 초기화(최초상태)
function initializeWebSocket() {
  sockJs = new SockJS("/stomp");
  stomp = Stomp.over(sockJs);

  stomp.connect({}, function () {
    console.log("WebSocket connected");
    subscribeAlarm();
    if (currentRoomNo) {
      subscribeToRoom(currentRoomNo);
    }
  });
}
$(document).ready(function () {
  // 소켓 열기
  // getMyInfo();
  $.ajax({
    type: "get",
    url: "/api/getInfo",
    success: (resp) => {
      uvo = resp;
      initializeWebSocket();
    },
  });
});
