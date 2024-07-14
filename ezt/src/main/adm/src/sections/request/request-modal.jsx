import axios from 'axios';
import PropTypes from 'prop-types';
import { useState, useEffect } from 'react';

import Tab from '@mui/material/Tab';
import Card from '@mui/material/Card';
import Grid from '@mui/material/Grid';
import TabList from '@mui/lab/TabList';
import Modal from '@mui/material/Modal';
import TabPanel from '@mui/lab/TabPanel';
import Button from '@mui/material/Button';
import TabContext from '@mui/lab/TabContext';
import Typography from '@mui/material/Typography';
import CardContent from '@mui/material/CardContent';

// import { fDateTime } from 'src/utils/format-time';
// import { fNumber } from 'src/utils/format-number';

import Scrollbar from 'src/components/scrollbar';
import Spinner from 'src/components/spinner/spinner';

import Member from './request-modal-mem';
import Request from './request-modal-req';
import Proposal from './request-modal-prop';
import Contract from './request-modal-cont';
import CttReport from './request-modal-cttReport';
import { style, buttonStyle, modalTitleStyle } from '../common-table/css';

export default function RequestModal({ requestNo, open, onClose }) {
  const [reqInfo, setReqInfo] = useState({});

  const [propInfo, setPropInfo] = useState({});

  const [contInfo, setContInfo] = useState({});

  const [memInfo, setMemInfo] = useState({});

  const [cttRptInfo, setCttRptInfo] = useState({});

  const [tabVal, setTabVal] = useState('1');

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setLoading(true);
    const getInfo = async () => {
      if (requestNo !== -1) {
        const data = await axios.get(`/adm/requestInfo/${requestNo}`);
        console.log(data.data);
        setReqInfo(data.data.reqInfo);
        setPropInfo(data.data.propInfo);
        setContInfo(data.data.contInfo);
        setMemInfo(data.data.memInfo);
        setCttRptInfo(data.data.cttRptInfo);
        setLoading(false);
      }
    };
    getInfo();
  }, [requestNo]);
  const handleClose = () => {
    onClose();
    setTabVal('1');
  };
  const handleTabChange = (event, newValue) => {
    setTabVal(newValue);
  };
  return (
    <Modal
      open={open}
      onClose={handleClose}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
    >
      <Card sx={style}>
        <Grid sx={modalTitleStyle}>
          <Typography variant="h6" component="h2">
            의뢰 정보
          </Typography>
        </Grid>
        {loading ? (
          <Spinner />
        ) : (
          <Scrollbar sx={{ height: '88%' }}>
            <CardContent>
              <TabContext value={tabVal}>
                <TabList
                  onChange={handleTabChange}
                  sx={{
                    position: 'sticky',
                    top: -10,
                    zIndex: 1,
                    backgroundColor: '#f9f9f9',
                    borderColor: 'divider',
                  }}
                >
                  <Tab label="의뢰 정보" value="1" />
                  {propInfo && <Tab label="견적서 정보" value="2" />}
                  {contInfo && <Tab label="계약서 정보" value="3" />}
                  {memInfo && <Tab label="팀 정보" value="4" />}
                  {cttRptInfo && <Tab label="중간 보고" value="5" />}
                </TabList>
                <TabPanel value="1" sx={{ p: 0, pt: 3 }}>
                  <Request reqInfo={reqInfo} />
                </TabPanel>
                {propInfo && (
                  <TabPanel value="2" sx={{ p: 0, pt: 3 }}>
                    <Proposal propInfo={propInfo} />
                  </TabPanel>
                )}
                {contInfo && (
                  <TabPanel value="3" sx={{ p: 0, pt: 3 }}>
                    <Contract contInfo={contInfo} reqInfo={reqInfo} />
                  </TabPanel>
                )}
                {memInfo && (
                  <TabPanel value="4" sx={{ p: 0, pt: 3 }}>
                    <Member memInfo={memInfo} />
                  </TabPanel>
                )}
                {cttRptInfo && (
                  <TabPanel value="5" sx={{ p: 0, pt: 3 }}>
                    <CttReport cttRptInfo={cttRptInfo} />
                  </TabPanel>
                )}
              </TabContext>
            </CardContent>
          </Scrollbar>
        )}
        {tabVal === '1' && (
          <Button
            variant="contained"
            fullWidth
            color="error"
            sx={{ ...buttonStyle }}
            onClick={() => {}}
          >
            삭제하기
          </Button>
        )}
      </Card>
    </Modal>
  );
}
RequestModal.propTypes = {
  requestNo: PropTypes.any,
  open: PropTypes.bool,
  onClose: PropTypes.func,
};
