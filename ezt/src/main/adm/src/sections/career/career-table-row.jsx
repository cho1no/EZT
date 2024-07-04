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
  let color = '';
  if (careerAccessTf === 'A02') {
    color = 'success';
  } else if (careerAccessTf === 'A01') {
    color = 'warning';
  } else {
    color = 'error';
  }
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
        <Label color={color}>{careerAccessTfNm}</Label>
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
