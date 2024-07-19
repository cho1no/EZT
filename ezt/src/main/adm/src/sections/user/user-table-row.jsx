import PropTypes from 'prop-types';

import Stack from '@mui/material/Stack';
import Avatar from '@mui/material/Avatar';
import TableRow from '@mui/material/TableRow';
import TableCell from '@mui/material/TableCell';
import Typography from '@mui/material/Typography';

import { fDateTime } from 'src/utils/format-time';

import Label from 'src/components/label';
// ----------------------------------------------------------------------

export default function UserTableRow({
  uno,
  name,
  email,
  id,
  phone,
  joinDt,
  role,
  state,
  stateNm,
  avatarUrl,
  onClick,
}) {
  return (
    <TableRow hover tabIndex={-1} onClick={onClick}>
      <TableCell component="th" scope="row" pl={5}>
        <Stack direction="row" alignItems="center" spacing={2}>
          <Avatar alt={name} src={avatarUrl} />
          <Typography variant="subtitle2" noWrap>
            {name}
          </Typography>
        </Stack>
      </TableCell>

      <TableCell>{email}</TableCell>
      <TableCell>{id}</TableCell>
      <TableCell>
        {phone ? phone.replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`) : 'null'}
      </TableCell>
      <TableCell>{fDateTime(joinDt, 'yy/MM/dd hh:mm')}</TableCell>

      <TableCell>{role}</TableCell>

      <TableCell align="center">
        <Label color={{ active: 'success', pause: 'warning', quit: 'error' }[state]}>
          {stateNm}
        </Label>
      </TableCell>
    </TableRow>
  );
}

UserTableRow.propTypes = {
  uno: PropTypes.any,
  name: PropTypes.any,
  email: PropTypes.any,
  id: PropTypes.any,
  phone: PropTypes.any,
  joinDt: PropTypes.any,
  role: PropTypes.any,
  state: PropTypes.any,
  stateNm: PropTypes.any,
  avatarUrl: PropTypes.any,
  onClick: PropTypes.func,
};
