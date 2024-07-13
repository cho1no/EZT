import axios from 'axios';
// eslint-disable-next-line import/no-extraneous-dependencies
import { useState, useEffect } from 'react';

import Stack from '@mui/material/Stack';
import Container from '@mui/material/Container';
import Grid from '@mui/material/Unstable_Grid2';
import Typography from '@mui/material/Typography';

import Spinner from 'src/components/spinner/spinner';

import RequestCard from '../request-card';
import RequestSort from '../request-sort';
import RequestFilters from '../request-filters';

// ----------------------------------------------------------------------

export const NoData = () => (
  <Grid mt={5}>
    <Typography align="center" variant="h5" paragraph>
      필터 결과 없음
    </Typography>
    <Typography align="center" variant="body1" paragraph>
      조건에 해당하는 결과가 없습니다. 다른 조건을 시도해 보세요.
    </Typography>
  </Grid>
);

export default function RequestView() {
  const [requests, setRequests] = useState([]);
  const [filteredData, setFilteredData] = useState([]);
  const [openFilter, setOpenFilter] = useState(false);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setFilteredData(requests);
  }, [requests]);

  useEffect(() => {
    getRequests();
  }, []);

  const getRequests = async () => {
    await axios.get('/adm/requestsInfo').then((resp) => {
      setRequests([...resp.data]);
      setLoading(false);
    });
  };

  const handleOpenFilter = () => {
    setOpenFilter(true);
  };

  const handleCloseFilter = () => {
    setOpenFilter(false);
  };

  const notFound = !filteredData.length;

  if (loading) {
    return <Spinner />;
  }
  return (
    <Container>
      <Stack
        direction="row"
        alignItems="center"
        flexWrap="wrap-reverse"
        justifyContent="space-between"
        sx={{ mb: 3 }}
      >
        <Typography variant="h4">의뢰 관리</Typography>
        <Stack direction="row" spacing={1} flexShrink={0} sx={{ my: 1 }}>
          <RequestFilters
            openFilter={openFilter}
            onOpenFilter={handleOpenFilter}
            onCloseFilter={handleCloseFilter}
            datas={requests}
            filterDatas={setFilteredData}
          />

          <RequestSort data={filteredData} setData={setFilteredData} />
        </Stack>
      </Stack>

      <Grid container spacing={3}>
        {filteredData.map((request) => (
          <Grid key={request.requestNo} xs={12} sm={6} md={3}>
            <RequestCard request={request} />
          </Grid>
        ))}
      </Grid>
      {notFound && <NoData />}
    </Container>
  );
}
