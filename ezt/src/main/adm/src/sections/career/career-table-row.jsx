import PropTypes from 'prop-types';

import TableRow from '@mui/material/TableRow';
import TableCell from '@mui/material/TableCell';

import { fDateTime } from 'src/utils/format-time';

import Label from 'src/components/label';
// ----------------------------------------------------------------------

export default function CareerTableRow({
  // selected,
  careerNo,
  usersName,
  careerInfo,
  careerAccessTf,
  careerAccessTfNm,
  writeDt,
  onClick,
}) {
  const textOverStyle = {
    maxWidth: '200px',
    whiteSpace: 'nowrap',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
  };
  return (
    <TableRow hover tabIndex={-1} onClick={onClick}>
      <TableCell component="th" style={{ fontWeight: 'bold' }} align="center">
        {careerNo}
      </TableCell>

      <TableCell>{usersName}</TableCell>
      <TableCell sx={textOverStyle}>{careerInfo}</TableCell>
      <TableCell align="center">
        <Label
          color={
            {
              A01: 'warning',
              A02: 'success',
              A03: 'error',
            }[careerAccessTf]
          }
        >
          {careerAccessTfNm}
        </Label>
      </TableCell>
      <TableCell>{fDateTime(writeDt, 'yy/MM/dd hh:mm')}</TableCell>
    </TableRow>
  );
}

CareerTableRow.propTypes = {
  careerNo: PropTypes.any,
  usersName: PropTypes.any,
  careerInfo: PropTypes.any,
  careerAccessTf: PropTypes.any,
  careerAccessTfNm: PropTypes.any,
  writeDt: PropTypes.any,
  onClick: PropTypes.func,
};
