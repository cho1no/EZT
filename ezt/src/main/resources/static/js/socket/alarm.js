/**
 * alarm.js
 * 알람 기능
 */
// 채팅 socket 연결
function subscribeAlarm() {
  stomp.subscribe(`/sub/alarm/${uno}`, function (info) {
    let data = JSON.parse(info.body);
    showNotification();
    $(".alarm-body")
      .prepend(drawAlarmHistory(data))
      .click(() => {
        showAlamDetail(data);
        oneRead(data);
      });
    alarmNew();
  });
  // 안읽음 표시 ajax
  $.ajax({
    type: "get",
    url: "/notReadAlarm/" + uno,
    success: function (res) {
      if (res > 0) {
        alarmNew();
      }
    },
  });
}
// alarm notification
function showNotification() {
  let alarm = $(".alarm-menu");

  let notification = $(
    `<div id="alarm-notification" class="alarm-notification">새 알림이 있습니다.</div>`
  );
  alarm.append(notification);

  setTimeout(function () {
    notification.addClass("alarm-show");
  }, 10);
  setTimeout(function () {
    notification.removeClass("alarm-show");
    setTimeout(function () {
      $(notification).remove();
    }, 500);
  }, 2000);
}

// alarm is not read?
function alarmNew() {
  let redCircle = $(".alarm-circle");
  if (redCircle.css("display") != "block") {
    redCircle.css("display", "block");
  }
}

// 채팅 보내기
function sendAlarm(
  data = { title: "test", content: "test alarm", usersNo: 10001 }
) {
  //   stomp.send(
  //     "/pub/alarm/message",
  //     {},
  //     JSON.stringify({
  //       title: data.title,
  //       content: data.content,
  //       usersNo: data.usersNo,
  //     })
  //   );

  var data = {
    JUMIN: "991109", // 생년월일
    NAME: "최원호", // 성명
    LNCAREANUMBER: "22", // 면허번호(발급지역)
    LNCYEARNUMBER: "22", // 면허번호(교부년도)
    LNCSERIALNUMBER: "030572", // 면허번호(일련번호)
    LNCNUMBER: "70", // 면허번호
    PWDSERIALNUMBER: "WI666G", // 암호일련번호
  };
  console.log(data);
  var url = "https://developers.nonghyup.com/";
  var add = "InquireDepositorAccountNumber.nh";
  $.ajax({
    url: url,
    type: "POST",
    headers: {
      Authorization: "Token ******************************",
      "Content-Type": "application/json;charset=UTF-8",
    },
    data: JSON.stringify(data),
    success: function (response) {
      console.log("Success:", response);
    },
    error: function (error) {
      console.error("Error:", error);
    },
  });
}

// 알림창 열고 닫기
function alarmWindowClose() {
  $(".alarm-window").css("display", "none");
}
function alarmWindowOpen() {
  alarmHistoryAjax();
  $(".alarm-window").css("display", "flex");
  $("#readAllAlarm").css("display", "block");
  $(".alarm-circle").css("display", "none");
}
// 알람 목록 ajax
function alarmHistoryAjax() {
  $(".alarm-body").html("");
  $.ajax({
    type: "get",
    url: "/alarm/" + uno,
    success: function (response) {
      $(response).each((i, e) => {
        $(".alarm-body").append(
          $(drawAlarmHistory(e)).click(() => {
            showAlamDetail(e);
            oneRead(e);
          })
        );
      });
      console.log(response);
    },
  });
}
// 알람 목록 Tag
function drawAlarmHistory(obj) {
  let date =
    obj.alarmTime == null
      ? formatDate(new Date())
      : formatDate(new Date(obj.alarmTime));
  return `<div class="alarmHistoryDiv cpointer" data-cno='${obj.AlarmNo}'>
            <p><b>${
              obj.title
            }</b><span class="alarmTime float-end">${date}</span></p>
            <p><span id="alarmContent">
                ${strSlice(obj.content)}
              </span>
              <span id="alarmReadTF" class="alarmReadTF float-end">
                ${obj.readTf == "Y" ? "읽음" : "읽지 않음"}
            </span></p>
            </div>`;
}

// 알람 자세히보기
function showAlamDetail(obj) {
  $(".alarm-body").html("");
  $("#alarmBack").css("display", "inline-block");
  $("#readAllAlarm").css("display", "none");
  $.ajax({
    type: "get",
    url: "/alarmDetail/" + obj.alarmNo,
    success: function (res) {
      console.log(res);
      $(".alarm-body").html(`
        <div id="alarmDetailDiv">
        <p><b>${res.title}</b></p>
        <p>${formatDate(new Date(res.alarmTime))}</p>
        <p>${res.content}</p>
        </div>
      `);
    },
  });
}
// 단건 읽음 처리
function oneRead(obj) {
  $.ajax({
    type: "put",
    url: "/alarmRead/" + obj.alarmNo,
    success: function (res) {
      console.log(res);
    },
  });
}

// 전체 읽음 처리
function allRead() {
  $.ajax({
    type: "put",
    url: "/allAlarmRead/" + uno,
    success: function (res) {
      console.log(res);
      $(".alarmReadTF").each((i, e) => {
        $(e).text("읽음");
      });
    },
  });
}
// click events
// 알림버튼 클릭
$(".alarm-menu").click(() => {
  $(".alarm-menu").toggleClass("opened");
  if ($(".alarm-menu").hasClass("opened")) {
    alarmWindowClose();
  } else {
    alarmWindowOpen();
  }
});
// 알림 전체 읽음 버튼
$("#readAllAlarm").click(() => {
  allRead();
});
$("#alarmBack").click(() => {
  $("#alarmBack").css("display", "none");
  $("#readAllAlarm").css("display", "block");
  alarmHistoryAjax();
});
// test용
$(".alarm-menuu").click(() => {
  sendAlarm();
});
