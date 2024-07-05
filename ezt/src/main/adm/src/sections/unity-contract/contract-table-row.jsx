import PropTypes from 'prop-types';

import TableRow from '@mui/material/TableRow';
import TableCell from '@mui/material/TableCell';

import { fDateTime } from 'src/utils/format-time';

import Label from 'src/components/label';
// ----------------------------------------------------------------------

export default function ContractTableRow({
  unityContractNo,
  title,
  changes,
  contractTermsContent,
  writeDt,
  useTf,
  basicContractTf,
  onClick,
}) {
  return (
    <TableRow hover tabIndex={-1} onClick={onClick}>
      <TableCell component="th" style={{ fontWeight: 'bold' }} align="center">
        {unityContractNo}
      </TableCell>
      <TableCell>{title}</TableCell>
      <TableCell>{changes}</TableCell>
      <TableCell>{fDateTime(writeDt, 'yy/MM/dd hh:mm')}</TableCell>
      <TableCell align="center">{useTf}개</TableCell>
      <TableCell align="center">
        <Label
          color={
            {
              Y: 'success',
              N: 'warning',
            }[basicContractTf]
          }
        >
          {basicContractTf}
        </Label>
      </TableCell>
    </TableRow>
  );
}

ContractTableRow.propTypes = {
  unityContractNo: PropTypes.any,
  title: PropTypes.any,
  changes: PropTypes.any,
  contractTermsContent: PropTypes.any,
  writeDt: PropTypes.any,
  useTf: PropTypes.any,
  basicContractTf: PropTypes.any,
  onClick: PropTypes.func,
};
