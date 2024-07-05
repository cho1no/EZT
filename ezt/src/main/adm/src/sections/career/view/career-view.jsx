// eslint-disable-next-line import/no-extraneous-dependencies
import axios from 'axios';
// eslint-disable-next-line import/no-extraneous-dependencies
// import Swal from 'sweetalert2';
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
import TextareaAutosize from '@mui/material/TextareaAutosize';

import { fDateTime } from 'src/utils/format-time';

import Scrollbar from 'src/components/scrollbar';

import CareerTableRow from '../career-table-row';
import CareerTableHead from '../career-table-head';
import CareerTableToolbar from '../career-table-toolbar';
import TableNoData from '../../common-table/table-no-data';
import TableEmptyRows from '../../common-table/table-empty-rows';
import { style, boxStyle, contentStyle, textareaStyle } from '../../common-table/css';
import { emptyRows, showAlert, applyFilter, getComparator } from '../../common-table/utils';
// ----------------------------------------------------------------------

export default function CareerPage() {
  const [career, setCareer] = useState([]);

  const [page, setPage] = useState(0);

  const [order, setOrder] = useState('asc');

  const [orderBy, setOrderBy] = useState('name');

  const [filterName, setFilterName] = useState('');

  const [rowsPerPage, setRowsPerPage] = useState(5);

  const [open, setOpen] = useState(false);

  const [openDeny, setOpenDeny] = useState(false);

  const [careerInfo, setCareerInfo] = useState({});

  const [denyReason, setDenyReason] = useState('');

  useEffect(() => {}, [careerInfo]);

  useEffect(() => {
    getCareer();
  }, []);

  const getCareer = async () => {
    await axios
      .get('/adm/careersInfo')
      .then((resp) => {
        setCareer(
          [...resp.data].map((_, index) => ({
            careerNo: _.careerNo,
            usersNo: _.usersNo,
            usersName: _.usersName,
            careerInfo: _.careerInfo,
            careerStartDt: _.careerStartDt,
            careerEndDt: _.careerEndDt,
            fileId: _.fileId,
            careerAccessTf: _.careerAccessTf,
            careerAccessTfNm: _.careerAccessTfNm,
            writeDt: _.writeDt,
          }))
        );
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
  const handleOpen = async (no) => {
    const resp = await axios.get(`/adm/careerInfo/${no}`);
    setCareerInfo(resp.data);
    setOpen(true);
  };
  // modal close
  const handleClose = () => {
    setOpen(false);
  };
  // 반려사유 modal open
  const handleOpenDeny = () => {
    setOpenDeny(true);
    setDenyReason('');
  };
  // 반려사유 modal close
  const handleCloseDeny = () => {
    setOpenDeny(false);
    setDenyReason('');
  };
  // 입력 받은 내용 useState반영
  const setInputValue = (event) => {
    const { value } = event.target;
    setDenyReason(value);
  };
  // 승인 처리
  const setCareerAccept = async () => {
    const accept = await axios.get(`/adm/careerAccept/${careerInfo.careerNo}`);
    if (accept.data === 1) {
      setCareer(updateCareer('A02', '승인'));
      setCareerInfo({ ...careerInfo, careerAccessTf: 'A02', careerAccessTfNm: '승인' });
      showAlert('success', '승인 처리 되었습니다.');
    } else {
      showAlert('error', '승인 처리에 실패했습니다.');
    }
  };
  // 반려사유 등록
  const postCareerDeny = async () => {
    const data = {
      content: denyReason,
      writer: 10000, // 로그인 JWT 받아와야함
      careerNo: careerInfo.careerNo,
    };
    const deny = await axios.post(`/adm/careerDeny`, data);
    if (deny.data === 1) {
      setCareer(updateCareer('A03', '반려'));
      setCareerInfo({ ...careerInfo, careerAccessTf: 'A03', careerAccessTfNm: '반려' });
      showAlert('success', '반려 처리 되었습니다.');
      handleCloseDeny();
    } else {
      showAlert('error', '반려 처리에 실패했습니다.');
    }
  };
  // list의 상태 업데이트 (state : 'A02'승인, 'A03'반려)
  const updateCareer = (state, stateNm) =>
    career.map((obj) => {
      if (obj.careerNo === careerInfo.careerNo) {
        return { ...obj, careerAccessTf: state, careerAccessTfNm: stateNm };
      }
      return obj;
    });
  const handleChangePage = (event, newPage) => {
    setPage(newPage);
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
    inputData: career,
    comparator: getComparator(order, orderBy),
    filterName,
  });
  const notFound = !dataFiltered.length && !!filterName;
  // eslint-disable-next-line react/no-unstable-nested-components
  function DenyBtn() {
    return (
      <Grid item xs>
        <Button variant="contained" fullWidth color="error" onClick={handleOpenDeny}>
          반려하기
        </Button>
      </Grid>
    );
  }
  // eslint-disable-next-line react/no-unstable-nested-components
  function AcceptBtn() {
    return (
      <Grid item xs>
        <Button variant="contained" fullWidth color="success" onClick={setCareerAccept}>
          승인하기
        </Button>
      </Grid>
    );
  }
  return (
    <>
      <Container>
        <Card>
          <CareerTableToolbar filterName={filterName} onFilterName={handleFilterByName} />

          <Scrollbar>
            <TableContainer sx={{ overflow: 'unset' }}>
              <Table sx={{ minWidth: 800 }}>
                <CareerTableHead
                  order={order}
                  orderBy={orderBy}
                  rowCount={career.length}
                  onRequestSort={handleSort}
                  headLabel={[
                    { id: 'careerNo', label: '요청번호', align: 'center' },
                    { id: 'usersName', label: '요청인' },
                    { id: 'careerInfo', label: '경력 내용' },
                    { id: 'careerAccessTf', label: '요청상태', align: 'center' },
                    { id: 'writeDt', label: '요청일' },
                  ]}
                />
                <TableBody>
                  {dataFiltered
                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                    .map((row) => (
                      <CareerTableRow
                        key={row.careerNo}
                        careerNo={row.careerNo}
                        usersName={row.usersName}
                        careerInfo={row.careerInfo}
                        careerAccessTf={row.careerAccessTf}
                        careerAccessTfNm={row.careerAccessTfNm}
                        writeDt={row.writeDt}
                        onClick={() => handleOpen(row.careerNo)}
                      />
                    ))}

                  <TableEmptyRows
                    height={77}
                    emptyRows={emptyRows(page, rowsPerPage, career.length)}
                  />

                  {notFound && <TableNoData query={filterName} />}
                </TableBody>
              </Table>
            </TableContainer>
          </Scrollbar>
          <TablePagination
            page={page}
            component="div"
            count={career.length}
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
          <Typography variant="h6" component="h2">
            경력 인증 요청 정보
          </Typography>
          <CardContent sx={contentStyle}>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <Typography variant="subtitle1">이름</Typography>
                <Typography style={boxStyle} variant="body1">
                  {careerInfo.usersName}
                </Typography>
              </Grid>
              <Grid item xs={12}>
                <Typography variant="subtitle1">경력 내용</Typography>
                <Typography style={boxStyle} variant="body1">
                  {careerInfo.careerInfo}
                </Typography>
              </Grid>
              <Grid item xs={12}>
                <Typography variant="subtitle1">경력 기간</Typography>
                <Grid item container>
                  <Grid item xs={5}>
                    <Typography style={boxStyle} variant="body1">
                      {fDateTime(careerInfo.careerStartDt, 'yyyy/MM/dd')}
                    </Typography>
                  </Grid>
                  <Grid item xs={2}>
                    <Typography lineHeight={3} align="center">
                      ~
                    </Typography>
                  </Grid>
                  <Grid item xs={5}>
                    <Typography style={boxStyle} variant="body1">
                      {fDateTime(careerInfo.careerEndDt, 'yyyy/MM/dd')}
                    </Typography>
                  </Grid>
                </Grid>
              </Grid>
              <Grid item xs={12}>
                <Typography variant="subtitle1">첨부 파일</Typography>
                <Typography style={boxStyle} variant="body1">
                  {careerInfo.fileId}
                </Typography>
              </Grid>
            </Grid>
          </CardContent>
          <Grid container spacing={2}>
            {
              {
                A01: (
                  <>
                    <AcceptBtn />
                    <DenyBtn />
                  </>
                ),
                A02: <DenyBtn />,
                A03: <AcceptBtn />,
              }[careerInfo.careerAccessTf]
            }
          </Grid>
        </Card>
      </Modal>
      <Modal
        open={openDeny}
        onClose={handleCloseDeny}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
        id="myModal"
      >
        <Card sx={{ ...style, width: '50%', height: '40%' }}>
          <Typography variant="h6" component="h2">
            반려사유 작성
          </Typography>
          <CardContent sx={contentStyle}>
            <TextareaAutosize
              id="denyReason"
              style={{ ...textareaStyle, height: 200 }}
              value={denyReason}
              onChange={setInputValue}
              required
            />
          </CardContent>
          <Button
            variant="contained"
            sx={{ bottom: '30px' }}
            fullWidth
            color="error"
            onClick={postCareerDeny}
          >
            반려확정
          </Button>
        </Card>
      </Modal>
    </>
  );
}
