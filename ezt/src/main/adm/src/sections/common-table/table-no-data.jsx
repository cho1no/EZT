import PropTypes from 'prop-types';

import Paper from '@mui/material/Paper';
import TableRow from '@mui/material/TableRow';
import TableCell from '@mui/material/TableCell';
import Typography from '@mui/material/Typography';

// ----------------------------------------------------------------------

export default function TableNoData({ query, colSpan }) {
  return (
    <TableRow>
      <TableCell align="center" colSpan={colSpan} sx={{ py: 3 }}>
        <Paper
          sx={{
            textAlign: 'center',
          }}
        >
          <Typography variant="h6" paragraph>
            검색 결과 없음
          </Typography>

          <Typography variant="body2">
            <strong>&quot;{query}&quot;</strong>에 대한 검색결과가 없습니다.
          </Typography>
        </Paper>
      </TableCell>
    </TableRow>
  );
}

TableNoData.propTypes = {
  query: PropTypes.string,
  colSpan: PropTypes.any,
};
