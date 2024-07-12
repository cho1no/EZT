import { useState } from 'react';
import PropTypes from 'prop-types';

import Menu from '@mui/material/Menu';
import Button from '@mui/material/Button';
import MenuItem from '@mui/material/MenuItem';
import { listClasses } from '@mui/material/List';
import Typography from '@mui/material/Typography';

import Iconify from 'src/components/iconify';

// ----------------------------------------------------------------------

const SORT_OPTIONS = [
  { value: 'newest', label: '최신순' },
  { value: 'oldest', label: '오래된순' },
];

export default function ShopProductSort({ data, setData }) {
  const [open, setOpen] = useState(null);
  const [selectedSort, setSelectedSort] = useState('newest');
  const handleOpen = (event) => {
    setOpen(event.currentTarget);
  };

  const handleClose = () => {
    setOpen(null);
  };
  const handleSortChange = (value) => {
    setSelectedSort(value);
    setOpen(null);
    let sortedData;
    if (value === 'newest') {
      sortedData = [...data].sort((a, b) => new Date(b.writeDt) - new Date(a.writeDt));
    } else if (value === 'oldest') {
      sortedData = [...data].sort((a, b) => new Date(a.writeDt) - new Date(b.writeDt));
    }
    setData(sortedData);
  };
  return (
    <>
      <Button
        disableRipple
        color="inherit"
        onClick={handleOpen}
        endIcon={<Iconify icon={open ? 'eva:chevron-up-fill' : 'eva:chevron-down-fill'} />}
      >
        정렬 :&nbsp;
        <Typography component="span" variant="subtitle2" sx={{ color: 'text.secondary' }}>
          {SORT_OPTIONS.find((option) => option.value === selectedSort).label}
        </Typography>
      </Button>

      <Menu
        open={!!open}
        anchorEl={open}
        onClose={handleClose}
        anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
        transformOrigin={{ vertical: 'top', horizontal: 'right' }}
        slotProps={{
          paper: {
            sx: {
              [`& .${listClasses.root}`]: {
                p: 0,
              },
            },
          },
        }}
      >
        {SORT_OPTIONS.map((option) => (
          <MenuItem
            key={option.value}
            selected={option.value === selectedSort}
            onClick={() => handleSortChange(option.value)}
          >
            {option.label}
          </MenuItem>
        ))}
      </Menu>
    </>
  );
}
ShopProductSort.propTypes = {
  data: PropTypes.any,
  setData: PropTypes.func,
};
