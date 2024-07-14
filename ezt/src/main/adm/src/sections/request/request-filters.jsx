import axios from 'axios';
import PropTypes from 'prop-types';
import { useState, useEffect } from 'react';

import Box from '@mui/material/Box';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import Drawer from '@mui/material/Drawer';
import Divider from '@mui/material/Divider';
import Typography from '@mui/material/Typography';
import IconButton from '@mui/material/IconButton';

import Iconify from 'src/components/iconify';
import Scrollbar from 'src/components/scrollbar';

import RequestOpts from './request-filters-option';

// ----------------------------------------------------------------------

export default function RequestFilters({
  openFilter,
  onOpenFilter,
  onCloseFilter,
  datas,
  filterDatas,
}) {
  const [regionOpt, setRegionOpt] = useState([]);
  const [reqStateOpt, setReqStateOpt] = useState([]);
  const [categoryOpt, setCategoryOpt] = useState([]);
  const [cttPlaceOpt, setCttPlaceOpt] = useState([]);
  const [isAllChecked, setIsAllChecked] = useState(true);

  useEffect(() => {
    getOptions();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  useEffect(() => {
    // 모든 옵션의 checked 상태를 확인하여 allUnchecked 상태를 업데이트
    const allChecked = [...regionOpt, ...reqStateOpt, ...categoryOpt, ...cttPlaceOpt].every(
      (opt) => !opt.checked
    );
    setIsAllChecked(allChecked);
    filterDatas(filteredData);

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [regionOpt, reqStateOpt, categoryOpt, cttPlaceOpt]);

  // 시작시 all true 세팅
  const setData = (data) =>
    data.map((_) => ({ codeNo: _.codeNo, codeName: _.codeName, checked: true }));
  // data 불러오기
  const getOptions = async () => {
    const regions = await axios.get('/adm/getCommonCodes/0B'); // 지역
    const reqState = await axios.get('/adm/getCommonCodes/0R'); // 상태
    const categories = await axios.get('/adm/getCommonCodes/0C'); // 분야
    const cttPlace = await axios.get('adm/getCommonCodes/0P'); // 주거공간
    setRegionOpt(setData(regions.data));
    setReqStateOpt(setData(reqState.data));
    setCategoryOpt(setData(categories.data));
    setCttPlaceOpt(setData(cttPlace.data));
  };
  // 입력 값에 따라 전체 체크 속성 바꾸기
  const checkAllByState = (state) => {
    setRegionOpt(regionOpt.map((_) => ({ ..._, checked: state })));
    setReqStateOpt(reqStateOpt.map((_) => ({ ..._, checked: state })));
    setCategoryOpt(categoryOpt.map((_) => ({ ..._, checked: state })));
    setCttPlaceOpt(cttPlaceOpt.map((_) => ({ ..._, checked: state })));
  };
  // 체크 상태 변경
  const handleCheckboxChange = (codeNo, list, setList) => {
    setList(list.map((opt) => (opt.codeNo === codeNo ? { ...opt, checked: !opt.checked } : opt)));
  };

  const filteredData = () => {
    const checkedRegionOpt = regionOpt.filter((opt) => opt.checked);
    const checkedCateOpt = categoryOpt.filter((opt) => opt.checked);
    const checkedReqStateOpt = reqStateOpt.filter((opt) => opt.checked);
    const checkedCttPlaceOpt = cttPlaceOpt.filter((opt) => opt.checked);
    let filter = datas.filter((data) =>
      checkedRegionOpt.some((opt) => opt.codeName === data.regionCode)
    );
    filter = filter.filter((data) =>
      checkedReqStateOpt.some((opt) => opt.codeName === data.requestStateNm)
    );
    filter = filter.filter((data) =>
      checkedCateOpt.some((opt) => opt.codeName === data.categoryCode)
    );
    filter = filter.filter((data) =>
      checkedCttPlaceOpt.some((opt) => opt.codeName === data.cttPlace)
    );
    return filter;
  };
  return (
    <>
      <Button
        disableRipple
        color="inherit"
        endIcon={<Iconify icon="ic:round-filter-list" />}
        onClick={onOpenFilter}
      >
        필터
      </Button>

      <Drawer
        anchor="right"
        open={openFilter}
        onClose={onCloseFilter}
        PaperProps={{
          sx: { width: 280, border: 'none', overflow: 'hidden' },
        }}
      >
        <Stack
          direction="row"
          alignItems="center"
          justifyContent="space-between"
          sx={{ px: 1, py: 2 }}
        >
          <Typography variant="h6" sx={{ ml: 1 }}>
            필터
          </Typography>
          <IconButton onClick={onCloseFilter}>
            <Iconify icon="eva:close-fill" />
          </IconButton>
        </Stack>

        <Divider />

        <Scrollbar>
          <Stack spacing={3} sx={{ p: 3 }}>
            <RequestOpts
              title="의뢰 공간"
              options={cttPlaceOpt}
              onChange={handleCheckboxChange}
              setOptions={setCttPlaceOpt}
            />
            <RequestOpts
              title="의뢰 분야"
              options={categoryOpt}
              onChange={handleCheckboxChange}
              setOptions={setCategoryOpt}
            />
            <RequestOpts
              title="의뢰 상태"
              options={reqStateOpt}
              onChange={handleCheckboxChange}
              setOptions={setReqStateOpt}
            />
            <RequestOpts
              title="의뢰 지역"
              options={regionOpt}
              onChange={handleCheckboxChange}
              setOptions={setRegionOpt}
            />
          </Stack>
        </Scrollbar>

        <Box sx={{ p: 3 }}>
          <Button
            fullWidth
            size="large"
            type="submit"
            color="inherit"
            variant="outlined"
            startIcon={<Iconify icon="ic:round-clear-all" />}
            onClick={() => {
              // eslint-disable-next-line no-unused-expressions
              isAllChecked ? checkAllByState(true) : checkAllByState(false);
            }}
          >
            {isAllChecked ? '전체 체크' : '전체 제거'}
          </Button>
        </Box>
      </Drawer>
    </>
  );
}

RequestFilters.propTypes = {
  openFilter: PropTypes.bool,
  onOpenFilter: PropTypes.func,
  onCloseFilter: PropTypes.func,
  datas: PropTypes.any,
  filterDatas: PropTypes.func,
};
