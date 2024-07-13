import { useState } from 'react';
import PropTypes from 'prop-types';

import Stack from '@mui/material/Stack';
import Checkbox from '@mui/material/Checkbox';
import Collapse from '@mui/material/Collapse';
import FormGroup from '@mui/material/FormGroup';
import Typography from '@mui/material/Typography';
import ExpandLess from '@mui/icons-material/ExpandLess';
import ExpandMore from '@mui/icons-material/ExpandMore';
import FormControlLabel from '@mui/material/FormControlLabel';

// ----------------------------------------------------------------------

export default function RequestOpts({ title, options, onChange, setOptions }) {
  const [open, setOpen] = useState(false);

  const handleClick = () => {
    setOpen(!open);
  };

  return (
    <Stack spacing={1}>
      <Typography variant="subtitle2" onClick={handleClick}>
        {title} {open ? <ExpandLess /> : <ExpandMore />}
      </Typography>
      <Collapse in={open} timeout="auto" unmountOnExit>
        <FormGroup>
          {options.map((item) => (
            <FormControlLabel
              key={item.codeNo}
              checked={item.checked}
              control={<Checkbox />}
              label={item.codeName}
              onChange={() => onChange(item.codeNo, options, setOptions)}
            />
          ))}
        </FormGroup>
      </Collapse>
    </Stack>
  );
}

RequestOpts.propTypes = {
  title: PropTypes.string,
  options: PropTypes.any,
  onChange: PropTypes.func,
  setOptions: PropTypes.func,
};
