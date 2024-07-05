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
import TextareaAutosize from '@mui/material/TextareaAutosize';

import { fDateTime } from 'src/utils/format-time';

import Scrollbar from 'src/components/scrollbar';

import ContractTableRow from '../contract-table-row';
import ContractTableHead from '../contract-table-head';
import TableNoData from '../../common-table/table-no-data';
import ContractTableToolbar from '../contract-table-toolbar';
import TableEmptyRows from '../../common-table/table-empty-rows';
import { style, contentStyle, textareaStyle } from '../../common-table/css';
import { emptyRows, showAlert, applyFilter, getComparator } from '../../common-table/utils';
// ----------------------------------------------------------------------

export default function ContractPage() {
  const [contracts, setContracts] = useState([]);

  const [page, setPage] = useState(0);

  const [order, setOrder] = useState('asc');

  const [orderBy, setOrderBy] = useState('name');

  const [filterName, setFilterName] = useState('');

  const [rowsPerPage, setRowsPerPage] = useState(5);

  const [open, setOpen] = useState(false);

  const [isEdit, setIsEdit] = useState(false);

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
            unityContractNo: _.unityContractNo,
            title: _.title,
            changes: _.changes,
            contractTermsContent: _.contractTermsContent,
            writeDt: _.writeDt,
            useTf: _.useTf,
            basicContractTf: _.basicContractTf,
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
    setOpen(true);
    const resp = await axios.get(`/adm/unityContractInfo/${no}`);
    setContractInfo(resp.data);
  };
  // modal close
  const handleClose = () => {
    setOpen(false);
    setIsEdit(false);
  };
  // 작성 상태 변경 (readOnly)
  const setEdit = () => {
    setIsEdit(true);
  };
  // 입력 받은 내용 useState반영
  const setInputValue = (event) => {
    const { id, value } = event.target;
    if (isEdit) setContractInfo({ ...contractInfo, [id]: value });
  };

  // 새 계약서 모달 func
  const openNewConstract = () => {
    setOpen(true);
    setIsEdit(true);
    setContractInfo({});
  };

  // 계약서 등록
  const postConstract = async () => {
    const postData = await axios.post('/adm/postUnityContract', contractInfo);
    console.log(postData.data);
    setContracts([postData.data, ...contracts]);
    showAlert('success', '계약서 등록 성공');
  };
  // 기본계약서 지정
  const putBasicContract = async () => {
    const putData = await axios.get(`/adm/putUnityBasic/${contractInfo.unityContractNo}`);
    if (putData.data > 0) {
      showAlert('success', '기본 계약서 변경 완료');
      setContracts(updateContracts());
      setContractInfo({ ...contractInfo, basicContractTf: 'Y' });
    } else {
      showAlert('error', '기본 계약서 변경 실패');
    }
  };
  // list의 상태 업데이트
  const updateContracts = () =>
    contracts.map((obj) => {
      if (obj.unityContractNo === contractInfo.unityContractNo) {
        return { ...obj, basicContractTf: 'Y' };
      }
      return { ...obj, basicContractTf: 'N' };
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
    inputData: contracts,
    comparator: getComparator(order, orderBy),
    filterName,
  });

  const notFound = !dataFiltered.length && !!filterName;

  const buttonStyle = {
    position: 'relative',
    display: contractInfo.contractsState === '회원 탈퇴' ? 'none' : '',
  };

  return (
    <>
      <Container>
        <Card>
          <ContractTableToolbar
            filterName={filterName}
            onFilterName={handleFilterByName}
            onNewContract={openNewConstract}
          />

          <Scrollbar>
            <TableContainer sx={{ overflow: 'unset' }}>
              <Table sx={{ minWidth: 800 }}>
                <ContractTableHead
                  order={order}
                  orderBy={orderBy}
                  rowCount={contracts.length}
                  onRequestSort={handleSort}
                  headLabel={[
                    { id: 'unityContractNo', label: '번호', align: 'center' },
                    { id: 'title', label: '제목' },
                    { id: 'changes', label: '변경사항' },
                    { id: 'writeDt', label: '작성일' },
                    { id: 'useTf', label: '사용여부', align: 'center' },
                    { id: 'basicContractTf', label: '기본 계약서 여부', align: 'center' },
                  ]}
                />
                <TableBody>
                  {dataFiltered
                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                    .map((row) => (
                      <ContractTableRow
                        key={row.unityContractNo}
                        unityContractNo={row.unityContractNo}
                        title={row.title}
                        changes={row.changes}
                        contractTermsContent={row.contractTermsContent}
                        writeDt={row.writeDt}
                        useTf={row.useTf}
                        basicContractTf={row.basicContractTf}
                        onClick={() => handleOpen(row.unityContractNo)}
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
        <Card sx={{ ...style, width: '50%' }}>
          <Grid sx={{ display: 'flex', justifyContent: 'space-between' }}>
            <Typography variant="h6" component="h2">
              통일 계약서 정보
            </Typography>
            {!isEdit && contractInfo.basicContractTf === 'N' && (
              <Button variant="contained" color="primary" onClick={putBasicContract}>
                기본 계약서로 지정
              </Button>
            )}
          </Grid>
          <CardContent sx={contentStyle}>
            <Grid container spacing={2}>
              <Grid item xs={12}>
                <Typography variant="subtitle1">제목</Typography>
                <TextField
                  variant="outlined"
                  id="title"
                  value={contractInfo.title || ''}
                  onChange={setInputValue}
                  fullWidth
                  required
                />
              </Grid>
              <Grid item xs={12}>
                <Typography variant="subtitle1">내용</Typography>
                <TextareaAutosize
                  id="contractTermsContent"
                  style={{ ...textareaStyle, overflow: 'hidden' }}
                  value={contractInfo.contractTermsContent || ''}
                  onChange={setInputValue}
                  required
                />
              </Grid>
              <Grid item xs={12}>
                <Typography variant="subtitle1">변경사항</Typography>
                <TextareaAutosize
                  id="changes"
                  style={{ ...textareaStyle, overflow: 'hidden' }}
                  value={contractInfo.changes || ''}
                  onChange={setInputValue}
                  required
                />
              </Grid>
              {!isEdit && (
                <>
                  <Grid item xs={12}>
                    <Typography variant="subtitle1">작성일</Typography>
                    <TextField
                      variant="outlined"
                      value={fDateTime(contractInfo.writeDt, 'yyyy/MM/dd hh:mm')}
                      fullWidth
                    />
                  </Grid>
                  <Grid item container spacing={2} xs={12}>
                    <Grid item xs={6}>
                      <Typography variant="subtitle1">기본 계약서 여부</Typography>
                      <TextField
                        variant="outlined"
                        value={contractInfo.basicContractTf || ''}
                        fullWidth
                      />
                    </Grid>
                    <Grid item xs={6}>
                      <Typography variant="subtitle1">사용 여부</Typography>
                      <TextField variant="outlined" value={contractInfo.useTf || ''} fullWidth />
                    </Grid>
                  </Grid>
                </>
              )}
            </Grid>
          </CardContent>
          <Button
            variant="contained"
            fullWidth
            color={isEdit ? 'success' : 'primary'}
            sx={buttonStyle}
            onClick={async () => {
              if (isEdit) {
                postConstract();
                handleClose();
              } else {
                setEdit();
              }
            }}
          >
            {isEdit ? '새 계약서로 저장하기' : '다시 쓰기'}
          </Button>
        </Card>
      </Modal>
    </>
  );
}
