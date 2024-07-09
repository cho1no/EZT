import PropTypes from 'prop-types';

import Box from '@mui/material/Box';
import Link from '@mui/material/Link';
import Card from '@mui/material/Card';
import Stack from '@mui/material/Stack';
import Typography from '@mui/material/Typography';

// import { fCurrency } from 'src/utils/format-number';

import { fDateTime } from 'src/utils/format-time';

import Label from 'src/components/label';

// ----------------------------------------------------------------------

export default function RequestCard({ request }) {
  const renderStatus = (
    <Box
      sx={{
        zIndex: 9,
        top: 16,
        right: 16,
        position: 'absolute',
      }}
    >
      <Label variant="filled" color="primary" sx={{ mr: 0.5 }}>
        {request.regionCode}
      </Label>
      <Label
        variant="filled"
        color={
          {
            S01: 'primary',
            S02: 'secondary',
            S03: 'info',
            S04: 'success',
            S05: 'warning',
            S06: 'error',
            S07: 'grey',
          }[request.requestState]
        }
      >
        {request.requestStateNm}
      </Label>
    </Box>
  );

  const renderImg = (
    <Box
      component="img"
      alt={request.saveName}
      // src={`C:/${request.savePath}${request.saveName}${request.originalFileName}`}
      src="/assets/images/products/product_23.jpg"
      sx={{
        top: 0,
        width: 1,
        height: 1,
        objectFit: 'cover',
        position: 'absolute',
      }}
    />
  );
  return (
    <Card>
      <Box sx={{ pt: '100%', position: 'relative' }}>
        {request.requestState && renderStatus}

        {renderImg}
      </Box>

      <Stack sx={{ p: 3 }}>
        <Link color="inherit" underline="hover" variant="subtitle2" noWrap>
          {request.title}
        </Link>

        <Stack direction="row" alignItems="center" justifyContent="space-between">
          <Typography variant="body2">{request.usersName}</Typography>
        </Stack>
        <Stack direction="row" alignItems="center" justifyContent="space-between">
          <Typography variant="body2">{request.cttPlace}</Typography>
          <Typography variant="body2">{fDateTime(request.writeDt, 'yy/MM/dd')}</Typography>
        </Stack>
      </Stack>
    </Card>
  );
}

RequestCard.propTypes = {
  request: PropTypes.object,
};
