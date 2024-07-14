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

import Label from 'src/components/label';

import { styleColBox } from '../common-table/css';

export default function Member({ memInfo }) {
  return (
    <Grid container spacing={2}>
      <Grid item xs={12}>
        <Card sx={styleColBox}>
          <Typography variant="subtitle1" p={1}>
            팀 정보
          </Typography>
          <TableContainer>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>담당자명</TableCell>
                  <TableCell>공사분야</TableCell>
                  <TableCell>전화번호</TableCell>
                  <TableCell>구분</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {memInfo.map((ele, idx) => (
                  <TableRow key={idx}>
                    <TableCell>{ele.usersName}</TableCell>
                    <TableCell>{ele.workCodeNm}</TableCell>
                    <TableCell>{ele.usersPhone}</TableCell>
                    <TableCell>
                      <Label color={ele.memberDivision === 'B' ? 'warning' : 'primary'}>
                        {ele.memberDivision === 'B' ? '팀장' : '팀원'}
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
Member.propTypes = {
  memInfo: PropTypes.any,
};
