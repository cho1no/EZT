// import { useState } from 'react';
import PropTypes from 'prop-types';
import 'react-responsive-carousel/lib/styles/carousel.min.css';

import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import Table from '@mui/material/Table';
import TableRow from '@mui/material/TableRow';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import Typography from '@mui/material/Typography';
import TableContainer from '@mui/material/TableContainer';

import { fDateTime } from 'src/utils/format-time';
import { fNumber } from 'src/utils/format-number';

import { boxStyle, styleColBox } from '../common-table/css';

export default function Contract({ contInfo, reqInfo }) {
  return (
    <Grid container spacing={2}>
      <Grid item xs={12}>
        <Card sx={styleColBox}>
          <ContInfo info={contInfo.contract} req={reqInfo} />
        </Card>
      </Grid>
      <Grid item xs={12}>
        <Card sx={styleColBox}>
          <ContDetail detail={contInfo.contract.dlist} />
        </Card>
      </Grid>
      <Grid item md={6} sm={12}>
        <Card sx={styleColBox}>
          <ContRequester user={contInfo.requester} info={contInfo.contract} />
        </Card>
      </Grid>
      <Grid item md={6} sm={12} xs={12}>
        <Card sx={styleColBox}>
          <ContWorker user={contInfo.worker} info={contInfo.contract} />
        </Card>
      </Grid>
      <Grid item xs={12}>
        <Card sx={styleColBox}>
          <Grid item xs={12}>
            <Typography variant="subtitle1" p={1}>
              기본 계약서 번호 : {contInfo.contract.unityContractNo}번
            </Typography>
          </Grid>
        </Card>
      </Grid>
    </Grid>
  );
}
Contract.propTypes = {
  contInfo: PropTypes.any,
  reqInfo: PropTypes.any,
};

function ContInfo({ info, req }) {
  return (
    <Grid item xs={12}>
      <Typography variant="subtitle1" p={1}>
        공사명
      </Typography>
      <Typography style={boxStyle} variant="body1">
        {info.cttName}
      </Typography>
      <Typography variant="subtitle1" p={1}>
        공사주소
      </Typography>
      <Typography style={boxStyle} variant="body1">
        ({req.postcode}) {req.address} {req.detailAddress}
      </Typography>
      <Grid container spacing={2}>
        <Grid item xs={6}>
          <Typography variant="subtitle1" p={1}>
            계약일
          </Typography>
          <Typography style={boxStyle} variant="body1">
            {info.updateDt
              ? fDateTime(info.updateDt, 'yyyy/MM/dd')
              : fDateTime(info.writeDt, 'yyyy/MM/dd')}
          </Typography>
        </Grid>
        <Grid item xs={6}>
          <Typography variant="subtitle1" p={1}>
            공사기간
          </Typography>
          <Typography style={boxStyle} variant="body1">
            {fDateTime(info.cttStartDt, 'yyyy/MM/dd')} ~ {fDateTime(info.cttEndDt, 'yyyy/MM/dd')}
          </Typography>
        </Grid>
      </Grid>
    </Grid>
  );
}
ContInfo.propTypes = {
  info: PropTypes.any,
  req: PropTypes.any,
};
function ContDetail({ detail }) {
  return (
    <Grid item xs={12}>
      <Grid sx={{ display: 'flex', justifyContent: 'space-between' }}>
        <Typography variant="subtitle1" p={1}>
          공사 대금
        </Typography>
        <Typography variant="body1" p={1}>
          합계 {fNumber(detail.reduce((sum, d) => sum + d.price, 0))}원
        </Typography>
      </Grid>
      <TableContainer>
        <Table size="small">
          <TableHead>
            <TableRow>
              <TableCell>회차</TableCell>
              <TableCell>내역</TableCell>
              <TableCell>금액</TableCell>
              <TableCell>지급일</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {detail.map((ele, idx) => (
              <TableRow key={idx}>
                <TableCell>{ele.round}</TableCell>
                <TableCell>{ele.history}</TableCell>
                <TableCell>{fNumber(ele.price)}원</TableCell>
                <TableCell>{fDateTime(ele.paymentDt, 'yyyy/MM/dd')}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Grid>
  );
}
ContDetail.propTypes = {
  detail: PropTypes.any,
};
function ContRequester({ user, info }) {
  return (
    <Grid item xs={12}>
      <Typography variant="subtitle1" p={1}>
        발주자(시행자) &quot;갑&quot;
      </Typography>
      <Typography style={boxStyle} variant="body1">
        {user.usersName}
      </Typography>
      <Typography variant="subtitle1" p={1}>
        전화번호
      </Typography>
      <Typography style={boxStyle} variant="body1">
        {user.usersPhone}
      </Typography>
      <Typography variant="subtitle1" p={1}>
        주소
      </Typography>
      <Typography style={{ ...boxStyle, whiteSpace: 'pre-line' }} variant="body1">
        {info.requesterAddress.replace(/_/g, '\n')}
      </Typography>
      <Typography variant="subtitle1" p={1}>
        계좌번호
      </Typography>
      <Typography style={boxStyle} variant="body1">
        {info.requesterBankcode} {info.requesterAccount}
      </Typography>
    </Grid>
  );
}
ContRequester.propTypes = {
  user: PropTypes.any,
  info: PropTypes.any,
};
function ContWorker({ user, info }) {
  return (
    <Grid item xs={12}>
      <Typography variant="subtitle1" p={1}>
        수급자(시공자) &quot;을&quot;
      </Typography>
      <Typography style={boxStyle} variant="body1">
        {user.usersName}
      </Typography>
      <Typography variant="subtitle1" p={1}>
        전화번호
      </Typography>
      <Typography style={boxStyle} variant="body1">
        {user.usersPhone}
      </Typography>
      <Typography variant="subtitle1" p={1}>
        주소
      </Typography>
      <Typography style={{ ...boxStyle, whiteSpace: 'pre-line' }} variant="body1">
        {info.workerAddress.replace(/_/g, '\n')}
      </Typography>
      <Typography variant="subtitle1" p={1}>
        계좌번호
      </Typography>
      <Typography style={boxStyle} variant="body1">
        {info.workerBankcode} {info.workerAccount}
      </Typography>
    </Grid>
  );
}
ContWorker.propTypes = {
  user: PropTypes.any,
  info: PropTypes.any,
};
