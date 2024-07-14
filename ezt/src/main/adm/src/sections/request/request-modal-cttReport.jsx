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

import Label from 'src/components/label';

import { styleColBox } from '../common-table/css';

export default function CttReport({ cttRptInfo }) {
  return (
    <Grid container spacing={2}>
      <Grid item xs={12}>
        <Card sx={styleColBox}>
          <Typography variant="subtitle1" p={1}>
            중간 보고
          </Typography>
          <TableContainer>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>번호</TableCell>
                  <TableCell>제목</TableCell>
                  <TableCell>구분</TableCell>
                  <TableCell>작성일시</TableCell>
                  <TableCell align="center">승인상태</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {cttRptInfo.map((ele, idx) => (
                  <TableRow key={idx}>
                    <TableCell>{ele.contractNo}</TableCell>
                    <TableCell>{ele.title}</TableCell>
                    <TableCell>{ele.cttDivision}</TableCell>
                    <TableCell>{fDateTime(ele.cttReportDt, 'yyyy/MM/dd')}</TableCell>
                    <TableCell align="center">
                      <Label color={ele.accessState === 'Y' ? 'primary' : 'error'}>
                        {ele.accessState === 'Y' ? '승인' : '미승인'}
                      </Label>
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
        </Card>
      </Grid>
    </Grid>
  );
}
CttReport.propTypes = {
  cttRptInfo: PropTypes.any,
};
