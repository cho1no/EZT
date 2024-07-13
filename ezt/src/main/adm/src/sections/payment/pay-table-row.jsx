import PropTypes from 'prop-types';

import TableRow from '@mui/material/TableRow';
import TableCell from '@mui/material/TableCell';

import { fDateTime } from 'src/utils/format-time';
import { fNumber } from 'src/utils/format-number';

import Label from 'src/components/label';
// ----------------------------------------------------------------------

export default function PayTableRow({ data, onClick }) {
  return (
    <TableRow hover tabIndex={-1} onClick={() => onClick(data.PAY_NO)}>
      <TableCell align="center">{data.no}</TableCell>
      <TableCell>{data.TITLE}</TableCell>
      <TableCell>{data.CATEGORY_CODE_NM}</TableCell>
      <TableCell>{fDateTime(data.PAY_DT, 'yyyy/MM/dd')}</TableCell>
      <TableCell>{fNumber(data.UNPAID_SUM)}원</TableCell>
      <TableCell>{fNumber(data.PRICE)}원</TableCell>

      <TableCell>{data.HISTORY}</TableCell>
      <TableCell>{fDateTime(data.PAYMENT_DT, 'yyyy/MM/dd')}</TableCell>

      <TableCell>
        <Label color={{ N: 'error', Y: 'success' }[data.PAYMENT_TF]}>
          {{ N: '미지급', Y: '지급' }[data.PAYMENT_TF]}
        </Label>
      </TableCell>
    </TableRow>
  );
}

PayTableRow.propTypes = {
  data: PropTypes.object,
  onClick: PropTypes.func,
};
