// import { useState } from 'react';
import PropTypes from 'prop-types';

// import Stack from '@mui/material/Stack';
// import Avatar from '@mui/material/Avatar';
// import Popover from '@mui/material/Popover';
import TableRow from '@mui/material/TableRow';
// import Checkbox from '@mui/material/Checkbox';
// import MenuItem from '@mui/material/MenuItem';
import TableCell from '@mui/material/TableCell';
// import Typography from '@mui/material/Typography';
// import IconButton from '@mui/material/IconButton';

// import Iconify from 'src/components/iconify';
import { fDateTime } from 'src/utils/format-time';

// import Label from 'src/components/label';
// ----------------------------------------------------------------------

export default function ContractTableRow({
  // selected,
  unityContractNo,
  title,
  changes,
  contractTermsContent,
  writeDt,
  useTf,
  basicContractTf,
  // name,
  // email,
  // id,
  // phone,
  // joinDt,
  // role,
  // state,
  // avatarUrl,
  onClick,
}) {
  // let color = '';
  // let koRole = '';
  // if (state === '활동 중') {
  //   color = 'success';
  // } else if (state === '활동 정지') {
  //   color = 'warning';
  // } else {
  //   color = 'error';
  // }
  // if (role === 'ROLE_USER') {
  //   koRole = '일반 유저';
  // } else if (role === 'ROLE_WORKER') {
  //   koRole = '작업자';
  // } else {
  //   koRole = '관리자';
  // }
  return (
    <TableRow hover tabIndex={-1} onClick={onClick}>
      <TableCell component="th" style={{ fontWeight: 'bold' }} align="center">
        {unityContractNo}
      </TableCell>

      <TableCell>{title}</TableCell>
      <TableCell>{changes}</TableCell>
      <TableCell>{fDateTime(writeDt, 'yy/MM/dd hh:mm')}</TableCell>
      <TableCell>{useTf}</TableCell>
      <TableCell>{basicContractTf}</TableCell>

      {/* <TableCell align="center">
        <Label color={color}>{state}</Label>
      </TableCell> */}
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
  // name: PropTypes.any,
  // email: PropTypes.any,
  // id: PropTypes.any,
  // phone: PropTypes.any,
  // joinDt: PropTypes.any,
  // role: PropTypes.any,
  // state: PropTypes.any,
  // avatarUrl: PropTypes.any,
  onClick: PropTypes.func,
};
