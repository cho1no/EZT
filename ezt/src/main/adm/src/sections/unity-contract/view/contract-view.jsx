// eslint-disable-next-line import/no-extraneous-dependencies
import axios from 'axios';
import { useState, useEffect } from 'react';

import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import Modal from '@mui/material/Modal';
import Table from '@mui/material/Table';
import Button from '@mui/material/Button';
import Container from '@mui/material/Container';
import TableBody from '@mui/material/TableBody';
import TextField from '@mui/material/TextField';
import Typography from '@mui/material/Typography';
import CardContent from '@mui/material/CardContent';
import TableContainer from '@mui/material/TableContainer';
import TablePagination from '@mui/material/TablePagination';
// import { fDateTime } from 'src/utils/format-time';

import { fDateTime } from 'src/utils/format-time';

import Scrollbar from 'src/components/scrollbar';

import TableNoData from '../../user/table-no-data';
import ContractTableRow from '../contract-table-row';
import ContractTableHead from '../contract-table-head';
import TableEmptyRows from '../../user/table-empty-rows';
import ContractTableToolbar from '../contract-table-toolbar';
import { emptyRows, applyFilter, getComparator } from '../utils';
// ----------------------------------------------------------------------

export default function ContractPage() {
  const [contracts, setContracts] = useState([]);

  const [page, setPage] = useState(0);

  const [order, setOrder] = useState('asc');

  const [orderBy, setOrderBy] = useState('name');

  const [filterName, setFilterName] = useState('');

  const [rowsPerPage, setRowsPerPage] = useState(5);

  const [open, setOpen] = useState(false);

  const [contractInfo, setContractInfo] = useState({});
  useEffect(() => {}, [contractInfo]);
  useEffect(() => {
    getContracts();
  }, []);
  const getContracts = async () => {
    await axios
      .get('/adm/unityContractsInfo')
      .then((resp) => {
        setContracts(
          [...resp.data].map((_, index) => ({
            no: _.unityContractNo,
            title: _.title,
            changes: _.changes,
            terms: _.contractTermsContent,
            writeDt: _.writeDt,
            useTf: _.useTf,
            basicTf: _.basicContractTf,
          }))
        );
        console.log(resp);
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
    setOpen(true);
    const resp = await axios.get(`/adm/unityContractInfo/${no}`);
    setContractInfo(resp.data);
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
        setContractInfo({ ...contractInfo, contractsState: '활동 정지' });
        getContracts();
      })
      .catch();
  };
  const setUserActive = async (uno) => {
    await axios
      .get(`adm/userActive/${uno}`)
      .then((resp) => {
        setContractInfo({ ...contractInfo, contractsState: '활동 중' });
        getContracts();
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
    inputData: contracts,
    comparator: getComparator(order, orderBy),
    filterName,
  });

  const notFound = !dataFiltered.length && !!filterName;
  const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: '60%',
    height: '80%',
    bgcolor: 'background.paper',
    boxShadow: 24,
    p: 3,
  };
  const contentStyle = {
    position: 'relative',
    overflowY: 'auto',
    height: '90%',
    paddingRight: 1,
    '&::-webkit-scrollbar': {
      width: '5px',
    },
    '&::-webkit-scrollbar-thumb': {
      background: '#888',
      borderRadius: '10px',
    },
  };
  const buttonStyle = {
    position: 'relative',
    display: contractInfo.contractsState === '회원 탈퇴' ? 'none' : '',
  };

  return (
    <>
      <Container>
        <Card>
          <ContractTableToolbar filterName={filterName} onFilterName={handleFilterByName} />

          <Scrollbar>
            <TableContainer sx={{ overflow: 'unset' }}>
              <Table sx={{ minWidth: 800 }}>
                <ContractTableHead
                  order={order}
                  orderBy={orderBy}
                  rowCount={contracts.length}
                  onRequestSort={handleSort}
                  headLabel={[
                    { id: 'no', label: '번호' },
                    { id: 'title', label: '제목' },
                    { id: 'changes', label: '변경사항' },
                    { id: 'writeDt', label: '작성일' },
                    { id: 'useTf', label: '사용여부' },
                    { id: 'basicTf', label: '기본 계약서 여부' },
                    // { id: 'contractsEmail', label: '이메일' },
                    // { id: 'contractsId', label: '아이디' },
                    // { id: 'contractsPhone', label: '전화번호' },
                    // { id: 'contractsJoinDt', label: '가입일' },
                    // { id: 'contractsRole', label: '구분' },
                    // { id: 'contractsState', label: '활동상태', align: 'center' },
                  ]}
                />
                <TableBody>
                  {dataFiltered
                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                    .map((row) => (
                      <ContractTableRow
                        key={row.no}
                        no={row.no}
                        title={row.title}
                        changes={row.changes}
                        terms={row.terms}
                        writeDt={row.writeDt}
                        useTf={row.useTf}
                        basicTf={row.basicTf}
                        // uno={row.contractsNo}
                        // name={row.contractsName}
                        // email={row.contractsEmail}
                        // id={row.contractsId}
                        // phone={row.contractsPhone}
                        // joinDt={row.contractsJoinDt}
                        // role={row.contractsRole}
                        // state={row.contractsState}
                        onClick={() => handleOpen(row.no)}
                      />
                    ))}

                  <TableEmptyRows
                    height={77}
                    emptyRows={emptyRows(page, rowsPerPage, contracts.length)}
                  />

                  {notFound && <TableNoData query={filterName} />}
                </TableBody>
              </Table>
            </TableContainer>
          </Scrollbar>
          <TablePagination
            page={page}
            component="div"
            count={contracts.length}
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
            통일 계약서 정보
          </Typography>
          <CardContent sx={contentStyle}>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <Typography variant="subtitle1">번호</Typography>
                <TextField
                  variant="outlined"
                  value={contractInfo.unityContractNo}
                  InputProps={{
                    readOnly: true,
                  }}
                  fullWidth
                />
              </Grid>
              <Grid item xs={12}>
                <Typography variant="subtitle1">제목</Typography>
                <TextField
                  variant="outlined"
                  value={contractInfo.title}
                  InputProps={{
                    readOnly: true,
                  }}
                  fullWidth
                />
              </Grid>
              <Grid item xs={12}>
                <Typography variant="subtitle1">내용</Typography>
                <TextField
                  variant="outlined"
                  value={contractInfo.contractTermsContent}
                  InputProps={{
                    readOnly: true,
                  }}
                  fullWidth
                />
              </Grid>
              <Grid item xs={12}>
                <Typography variant="subtitle1">변경사항</Typography>
                <TextField
                  variant="outlined"
                  value={contractInfo.changes}
                  InputProps={{
                    readOnly: true,
                  }}
                  fullWidth
                />
              </Grid>
              <Grid item xs={12}>
                <Typography variant="subtitle1">작성일</Typography>
                <TextField
                  variant="outlined"
                  value={fDateTime(contractInfo.writeDt, 'yyyy/MM/dd hh:mm')}
                  InputProps={{
                    readOnly: true,
                  }}
                  fullWidth
                />
              </Grid>
            </Grid>
          </CardContent>
          <Button
            variant="contained"
            fullWidth
            color={contractInfo.contractsState === '활동 중' ? 'error' : 'success'}
            sx={buttonStyle}
            onClick={() =>
              contractInfo.contractsState === '활동 중'
                ? setUserPause(contractInfo.contractsNo)
                : setUserActive(contractInfo.contractsNo)
            }
          >
            {contractInfo.contractsState === '활동 중' ? '정지하기' : '정지해제'}
          </Button>
        </Card>
      </Modal>
    </>
  );
}
