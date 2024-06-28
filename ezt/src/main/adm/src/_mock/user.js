// eslint-disable-next-line import/no-extraneous-dependencies
import axios from 'axios';
// ----------------------------------------------------------------------
let data = [];

await axios
  .get('/adm/usersInfo')
  .then((resp) => {
    data = [...resp.data].map((_, index) => ({
      usersNo: _.usersNo,
      usersName: _.usersName ? _.usersName : '이름 없음',
      usersEmail: _.usersEmail ? _.usersEmail : '이메일 없음',
      usersId: _.usersId,
      usersPhone: _.usersPhone ? _.usersPhone : '전화번호 없음',
      usersJoinDt: _.usersJoinDt,
      usersRole: _.usersRole,
      usersState: _.usersState,
    }));
  })
  .catch(() => {});

export const users = data;
