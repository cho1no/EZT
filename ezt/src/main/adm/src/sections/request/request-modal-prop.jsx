import PropTypes from 'prop-types';
import 'react-responsive-carousel/lib/styles/carousel.min.css';

import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import Table from '@mui/material/Table';
import TableRow from '@mui/material/TableRow';
import Accordion from '@mui/material/Accordion';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableHead from '@mui/material/TableHead';
import Typography from '@mui/material/Typography';
import TableContainer from '@mui/material/TableContainer';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import AccordionDetails from '@mui/material/AccordionDetails';
import AccordionSummary from '@mui/material/AccordionSummary';

import { fDateTime } from 'src/utils/format-time';
import { fNumber } from 'src/utils/format-number';

import { styleColBox } from '../common-table/css';

export default function Proposal({ propInfo }) {
  return (
    <Grid container spacing={2}>
      <Grid item xs={12}>
        <Card sx={styleColBox}>
          <PropAccordion info={propInfo} />
        </Card>
      </Grid>
    </Grid>
  );
}
Proposal.propTypes = {
  propInfo: PropTypes.any,
};

function PropAccordion({ info }) {
  return (
    <Grid container spacing={2}>
      <Grid item xs={12}>
        {info.map((ele, idx) => (
          <Accordion key={idx}>
            <AccordionSummary expandIcon={<ExpandMoreIcon />}>
              <Grid container>
                <Typography variant="subtitle2">{ele.usersName}님의 견적서</Typography>
                <Grid item xs={12} sx={{ display: 'flex', justifyContent: 'space-between' }}>
                  <Typography variant="body2">{ele.comments}</Typography>
                  <Typography variant="body2">{fDateTime(ele.writeDt, 'yyyy/MM/dd')}</Typography>
                </Grid>
              </Grid>
            </AccordionSummary>
            <AccordionDetails>
              <PropTable list={ele.list} stDt={ele.cttStartDt} enDt={ele.cttEndDt} />
            </AccordionDetails>
          </Accordion>
        ))}
      </Grid>
    </Grid>
  );
}
PropAccordion.propTypes = {
  info: PropTypes.any,
};
function PropTable({ list, stDt, enDt }) {
  return (
    <>
      <Typography variant="body1" sx={{ float: 'right' }}>
        공사 예정 : {fDateTime(stDt, 'yyyy/MM/dd')} ~ {fDateTime(enDt, 'yyyy/MM/dd')}
      </Typography>
      <TableContainer>
        <Table size="small">
          <TableHead>
            <TableRow>
              <TableCell>품명</TableCell>
              <TableCell>단위</TableCell>
              <TableCell>수량</TableCell>
              <TableCell>단가</TableCell>
              <TableCell>금액</TableCell>
              <TableCell>비고</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {list.map((ele, idx) => (
              <TableRow key={idx}>
                <TableCell>{ele.product}</TableCell>
                <TableCell>{ele.unit}</TableCell>
                <TableCell>{ele.qty}</TableCell>
                <TableCell>{fNumber(ele.unitprice)}원</TableCell>
                <TableCell>{fNumber(ele.qty * ele.unitprice)}원</TableCell>
                <TableCell>{ele.comment || '없음'}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </>
  );
}
PropTable.propTypes = {
  list: PropTypes.any,
  stDt: PropTypes.any,
  enDt: PropTypes.any,
};
