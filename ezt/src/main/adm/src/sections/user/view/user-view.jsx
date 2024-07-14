import axios from 'axios';
import { useState, useEffect } from 'react';

import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import Modal from '@mui/material/Modal';
import Table from '@mui/material/Table';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import TableBody from '@mui/material/TableBody';
import Typography from '@mui/material/Typography';
import CardContent from '@mui/material/CardContent';
import TableContainer from '@mui/material/TableContainer';
import TablePagination from '@mui/material/TablePagination';

import { fDateTime } from 'src/utils/format-time';

import Scrollbar from 'src/components/scrollbar';
import Spinner from 'src/components/spinner/spinner';

import UserTableRow from '../user-table-row';
import UserTableHead from '../user-table-head';
import UserTableToolbar from '../user-table-toolbar';
import TableNoData from '../../common-table/table-no-data';
import TableEmptyRows from '../../common-table/table-empty-rows';
import { emptyRows, applyFilter, getComparator } from '../../common-table/utils';
import { style, boxStyle, buttonStyle, modalTitleStyle } from '../../common-table/css';

// ----------------------------------------------------------------------

export default function UserPage() {
  const [users, setUsers] = useState([]);

  const [page, setPage] = useState(0);

  const [order, setOrder] = useState('asc');

  const [orderBy, setOrderBy] = useState('name');

  const [filterName, setFilterName] = useState('');

  const [rowsPerPage, setRowsPerPage] = useState(5);

  const [open, setOpen] = useState(false);

  const [userInfo, setUserInfo] = useState({});

  const [loading, setLoading] = useState(true);

  useEffect(() => {}, [userInfo]);

  useEffect(() => {
    getUsers();
  }, []);

  const getUsers = async () => {
    await axios
      .get('/adm/usersInfo')
      .then((resp) => {
        setUsers(resp.data);
        setLoading(false);
      })
      .catch(() => {});
  };

  const handleSort = (event, id) => {
    const isAsc = orderBy === id && order === 'asc';
    if (id !== '') {
      setOrder(isAsc ? 'desc' : 'asc');
      setOrderBy(id);
    }
  };

  // modal open
  const handleOpen = async (userNo) => {
    setOpen(true);
    const resp = await axios.get(`/adm/userInfo/${userNo}`);
    setUserInfo(resp.data);
  };

  // modal close
  const handleClose = () => {
    setOpen(false);
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const setUserPause = async (uno) => {
    await axios
      .get(`adm/userPause/${uno}`)
      .then((resp) => {
        setUserInfo({ ...userInfo, usersState: '활동 정지' });
        getUsers();
      })
      .catch();
  };

  const setUserActive = async (uno) => {
    await axios
      .get(`adm/userActive/${uno}`)
      .then((resp) => {
        setUserInfo({ ...userInfo, usersState: '활동 중' });
        getUsers();
      })
      .catch();
  };

  const handleChangeRowsPerPage = (event) => {
    setPage(0);
    setRowsPerPage(parseInt(event.target.value, 10));
  };

  const handleFilterByName = (event) => {
    setPage(0);
    setFilterName(event.target.value);
  };

  const dataFiltered = applyFilter({
    inputData: users,
    comparator: getComparator(order, orderBy),
    filterName,
    filters: ['usersName', 'usersEmail', 'usersId', 'usersStateNm', 'usersRole', 'usersPhone'],
  });

  const notFound = !dataFiltered.length && !!filterName;

  function getRole(role) {
    if (role === 'ROLE_USER') {
      return '일반 유저';
    }
    if (role === 'ROLE_WORKER') {
      return '작업자';
    }
    return '관리자';
  }

  if (loading) {
    return <Spinner />;
  }

  return (
    <>
      <Container>
        <Card>
          <UserTableToolbar filterName={filterName} onFilterName={handleFilterByName} />

          <Scrollbar>
            <TableContainer sx={{ overflow: 'unset' }}>
              <Table sx={{ minWidth: 800 }}>
                <UserTableHead
                  order={order}
                  orderBy={orderBy}
                  rowCount={users.length}
                  onRequestSort={handleSort}
                  headLabel={[
                    { id: 'usersName', label: '이름' },
                    { id: 'usersEmail', label: '이메일' },
                    { id: 'usersId', label: '아이디' },
                    { id: 'usersPhone', label: '전화번호' },
                    { id: 'usersJoinDt', label: '가입일' },
                    { id: 'usersRole', label: '구분' },
                    { id: 'usersStateNm', label: '활동상태', align: 'center' },
                  ]}
                />
                <TableBody>
                  {dataFiltered
                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                    .map((row) => (
                      <UserTableRow
                        key={row.usersNo}
                        uno={row.usersNo}
                        name={row.usersName}
                        email={row.usersEmail}
                        id={row.usersId}
                        phone={row.usersPhone}
                        joinDt={row.usersJoinDt}
                        role={row.usersRole}
                        state={row.usersState}
                        stateNm={row.usersStateNm}
                        onClick={() => handleOpen(row.usersNo)}
                      />
                    ))}

                  <TableEmptyRows
                    height={77}
                    emptyRows={emptyRows(page, rowsPerPage, users.length)}
                  />

                  {notFound && <TableNoData query={filterName} colSpan={7} />}
                </TableBody>
              </Table>
            </TableContainer>
          </Scrollbar>

          <TablePagination
            page={page}
            component="div"
            count={users.length}
            rowsPerPage={rowsPerPage}
            onPageChange={handleChangePage}
            rowsPerPageOptions={[5, 10, 25]}
            onRowsPerPageChange={handleChangeRowsPerPage}
          />
        </Card>
      </Container>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Card sx={style}>
          <Grid sx={modalTitleStyle}>
            <Typography variant="h6" component="h2">
              회원정보
            </Typography>
          </Grid>
          <Scrollbar sx={{ height: '90%' }}>
            <CardContent>
              <Grid container spacing={2}>
                <Grid item xs={12}>
                  <Typography variant="subtitle1">이름</Typography>
                  <Typography style={boxStyle} variant="body1">
                    {userInfo.usersName}
                  </Typography>
                </Grid>
                <Grid item xs={12}>
                  <Typography variant="subtitle1">닉네임</Typography>
                  <Typography style={boxStyle} variant="body1">
                    {userInfo.usersNick ? userInfo.usersNick : '닉네임 없음'}
                  </Typography>
                </Grid>
                <Grid item xs={12}>
                  <Typography variant="subtitle1">이메일</Typography>
                  <Typography style={boxStyle} variant="body1">
                    {userInfo.usersEmail}
                  </Typography>
                </Grid>
                <Grid item xs={12}>
                  <Typography variant="subtitle1">구분</Typography>
                  <Typography style={boxStyle} variant="body1">
                    {getRole(userInfo.usersRole)}
                  </Typography>
                </Grid>
                <Grid item xs={12}>
                  <Typography variant="subtitle1">아이디</Typography>
                  <Typography style={boxStyle} variant="body1">
                    {userInfo.usersId}
                  </Typography>
                </Grid>
                <Grid item xs={12}>
                  <Typography variant="subtitle1">생년월일</Typography>
                  <Typography style={boxStyle} variant="body1">
                    {userInfo.usersBirth ? userInfo.usersBirth : '생년월일 없음'}
                  </Typography>
                </Grid>
                <Grid item xs={12}>
                  <Typography variant="subtitle1">성별</Typography>
                  <Typography style={boxStyle} variant="body1">
                    {userInfo.usersGender ? userInfo.usersGender : '성별 없음'}
                  </Typography>
                </Grid>
                <Grid item xs={12}>
                  <Typography variant="subtitle1">전화번호</Typography>
                  <Typography style={boxStyle} variant="body1">
                    {userInfo.usersPhone ? userInfo.usersPhone : '전화번호 없음'}
                  </Typography>
                </Grid>
                <Grid item xs={12}>
                  <Typography variant="subtitle1">가입일</Typography>
                  <Typography style={boxStyle} variant="body1">
                    {userInfo.usersJoinDt
                      ? fDateTime(userInfo.usersJoinDt, 'yy/MM/dd hh:mm')
                      : '가입일 없음'}
                  </Typography>
                </Grid>
                <Grid item xs={12}>
                  <Typography variant="subtitle1">상태 변경일</Typography>
                  <Typography style={boxStyle} variant="body1">
                    {userInfo.usersStateChangeDt
                      ? fDateTime(userInfo.usersStateChangeDt, 'yy/MM/dd hh:mm')
                      : '없음'}
                  </Typography>
                </Grid>
                <Grid item xs={12}>
                  <Typography variant="subtitle1">상태</Typography>
                  <Typography style={boxStyle} variant="body1">
                    {userInfo.usersState}
                  </Typography>
                </Grid>
              </Grid>
            </CardContent>
          </Scrollbar>
          <Button
            variant="contained"
            fullWidth
            color={userInfo.usersState === '활동 중' ? 'error' : 'success'}
            sx={{ ...buttonStyle, display: userInfo.usersState === '회원 탈퇴' ? 'none' : '' }}
            onClick={() =>
              userInfo.usersState === '활동 중'
                ? setUserPause(userInfo.usersNo)
                : setUserActive(userInfo.usersNo)
            }
          >
            {userInfo.usersState === '활동 중' ? '정지하기' : '정지해제'}
          </Button>
        </Card>
      </Modal>
    </>
  );
}
