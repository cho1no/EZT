import PropTypes from 'prop-types';

import Grid from '@mui/material/Grid';
// import Button from '@mui/material/Button';
// import Tooltip from '@mui/material/Tooltip';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
// import IconButton from '@mui/material/IconButton';
import OutlinedInput from '@mui/material/OutlinedInput';
import InputAdornment from '@mui/material/InputAdornment';

import Iconify from 'src/components/iconify';

// ----------------------------------------------------------------------

export default function CareerTableToolbar({
  //  numSelected,
  filterName,
  onFilterName,
  onNewContract,
}) {
  return (
    <Toolbar
      sx={{
        height: 96,
        display: 'flex',
        justifyContent: 'space-between',
      }}
    >
      <Grid container spacing={2}>
        <Grid item>
          <Typography variant="h4">통일 계약서 관리</Typography>
        </Grid>
      </Grid>
      <OutlinedInput
        value={filterName}
        onChange={onFilterName}
        placeholder="제목으로 검색"
        startAdornment={
          <InputAdornment position="start">
            <Iconify
              icon="eva:search-fill"
              sx={{ color: 'text.disabled', width: 20, height: 20 }}
            />
          </InputAdornment>
        }
      />
    </Toolbar>
  );
}

CareerTableToolbar.propTypes = {
  // numSelected: PropTypes.number,
  filterName: PropTypes.string,
  onFilterName: PropTypes.func,
  onNewContract: PropTypes.func,
};
