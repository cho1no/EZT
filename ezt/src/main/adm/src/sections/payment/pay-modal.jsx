import axios from 'axios';
import PropTypes from 'prop-types';
import { useState, useEffect } from 'react';

import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import Modal from '@mui/material/Modal';
import Table from '@mui/material/Table';
import TableRow from '@mui/material/TableRow';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import Typography from '@mui/material/Typography';
import CardContent from '@mui/material/CardContent';

import { fDateTime } from 'src/utils/format-time';
import { fNumber } from 'src/utils/format-number';

import Scrollbar from 'src/components/scrollbar';
import Spinner from 'src/components/spinner/spinner';

import { style, boxStyle, styleColBox, modalTitleStyle } from '../common-table/css';

export default function PayModal({ payNo, open, onClose }) {
  const [info, setInfo] = useState({});

  const [detail, setDetail] = useState({});

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    const getInfo = async () => {
      if (payNo !== -1) {
        const data = await axios.get(`/adm/paymentInfo/${payNo}`);
        console.log(data.data);
        setInfo(data.data.info);
        setDetail(data.data.details);
        setLoading(false);
        console.log(data.data);
      }
    };
    getInfo();
  }, [payNo]);
  return (
    <Modal
      open={open}
      onClose={onClose}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <Card sx={style}>
        <Grid sx={modalTitleStyle}>
          <Typography variant="h6" component="h2">
            결제 상세 정보
          </Typography>
        </Grid>
        {loading ? (
          <Spinner />
        ) : (
          <Scrollbar sx={{ height: '92%' }}>
            <CardContent>
              <Grid container spacing={2}>
                <Grid item xs={6}>
                  <Card sx={styleColBox}>
                    <Typography variant="subtitle1" p={1}>
                      의뢰인(갑)
                    </Typography>
                    <Typography style={boxStyle} variant="body1">
                      {info.REQUESTER_NAME}
                    </Typography>
                    <Typography variant="subtitle1" p={1}>
                      의뢰인 계좌번호
                    </Typography>
                    <Typography style={boxStyle} variant="body1">
                      {info.REQUESTER_ACCOUNT}
                    </Typography>
                  </Card>
                </Grid>
                <Grid item xs={6}>
                  <Card sx={styleColBox}>
                    <Typography variant="subtitle1" p={1}>
                      작업자(을)
                    </Typography>
                    <Typography style={boxStyle} variant="body1">
                      {info.WORKER_NAME}
                    </Typography>
                    <Typography variant="subtitle1" p={1}>
                      작업자 계좌번호
                    </Typography>
                    <Typography style={boxStyle} variant="body1">
                      {info.WORKER_ACCOUNT}
                    </Typography>
                  </Card>
                </Grid>
                <Grid item xs={12}>
                  <Card sx={styleColBox}>
                    <Grid container spacing={2}>
                      <Grid item xs={6}>
                        <Typography variant="subtitle1" p={1}>
                          잔여금
                        </Typography>
                        <Typography style={boxStyle} variant="body1">
                          {fNumber(info.UNPAID_SUM)}원
                        </Typography>
                      </Grid>
                      <Grid item xs={6}>
                        <Typography variant="subtitle1" p={1}>
                          총 금액
                        </Typography>
                        <Typography style={boxStyle} variant="body1">
                          {fNumber(info.PRICE)}원
                        </Typography>
                      </Grid>
                      <Grid item xs={12}>
                        <Typography variant="subtitle1" p={1}>
                          계약일
                        </Typography>
                        <Typography style={boxStyle} variant="body1">
                          {fDateTime(info.PAY_DT, 'yyyy/MM/dd HH:mm')}
                        </Typography>
                      </Grid>
                      <Grid item xs={6}>
                        <Typography variant="subtitle1" p={1}>
                          결제진행단계
                        </Typography>
                        <Typography style={boxStyle} variant="body1">
                          {info.HISTORY || 'null'}
                        </Typography>
                      </Grid>
                      <Grid item xs={6}>
                        <Typography variant="subtitle1" p={1}>
                          상태
                        </Typography>
                        <Typography style={boxStyle} variant="body1">
                          {{ N: '미지급', Y: '지급' }[info.PAYMENT_TF]}
                        </Typography>
                      </Grid>
                      <Grid item xs={12}>
                        <Typography variant="subtitle1" p={1}>
                          히스토리
                        </Typography>
                        <Table size="small" aria-label="a dense table">
                          <TableHead>
                            <TableRow>
                              <TableCell>회차</TableCell>
                              <TableCell>내역</TableCell>
                              <TableCell>금액</TableCell>
                              <TableCell>지급일</TableCell>
                              <TableCell>상태</TableCell>
                            </TableRow>
                          </TableHead>
                          <TableBody>
                            {detail.map((_) => (
                              <TableRow>
                                <TableCell>{_.round}</TableCell>
                                <TableCell> {_.history} </TableCell>
                                <TableCell> {fNumber(_.price)}원</TableCell>
                                <TableCell>{fDateTime(_.paymentDt, 'yyyy/MM/dd')}</TableCell>
                                <TableCell>{{ N: '미지급', Y: '지급' }[_.paymentTf]}</TableCell>
                              </TableRow>
                            ))}
                          </TableBody>
                        </Table>
                      </Grid>
                    </Grid>
                  </Card>
                </Grid>
              </Grid>
            </CardContent>
          </Scrollbar>
        )}
      </Card>
    </Modal>
  );
}
PayModal.propTypes = {
  payNo: PropTypes.any,
  open: PropTypes.bool,
  onClose: PropTypes.func,
};
