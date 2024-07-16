import PropTypes from 'prop-types';

import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import Stack from '@mui/material/Stack';
import Typography from '@mui/material/Typography';

import { fDateTime } from 'src/utils/format-time';

import Label from 'src/components/label';

// ----------------------------------------------------------------------

export default function RequestCard({ request, onClick }) {
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
      alt={`${request.savePath}${request.saveName}_${request.originalFileName}.${request.ext}`}
      src={`/home/ec2-user/upload/${request.savePath}${request.saveName}_${request.originalFileName}.${request.ext}`}
      // src="/assets/images/products/product_23.jpg"
      sx={{
        cursor: 'pointer',
        top: 0,
        width: 1,
        height: 1,
        objectFit: 'cover',
        position: 'absolute',
      }}
    />
  );
  return (
    <Card
      onClick={() => {
        onClick(request.requestNo);
      }}
    >
      <Box sx={{ pt: '100%', position: 'relative' }}>
        {request.requestState && renderStatus}

        {renderImg}
      </Box>

      <Stack sx={{ p: 3 }}>
        <Typography color="inherit" variant="subtitle1" noWrap>
          {request.title}
        </Typography>

        <Stack direction="row" alignItems="center" justifyContent="space-between">
          <Typography variant="body2">{request.cttPlace}</Typography>
          <Typography variant="body2">{request.categoryCode}</Typography>
        </Stack>
        <Stack direction="row" alignItems="center" justifyContent="space-between">
          <Typography variant="body2">{request.usersName}</Typography>
          <Typography variant="body2">{fDateTime(request.writeDt, 'yy/MM/dd')}</Typography>
        </Stack>
      </Stack>
    </Card>
  );
}

RequestCard.propTypes = {
  request: PropTypes.object,
  onClick: PropTypes.func,
};
