import axios from 'axios';
import { useState, useEffect } from 'react';

import Stack from '@mui/material/Stack';
import Container from '@mui/material/Container';
import Grid from '@mui/material/Unstable_Grid2';
import Typography from '@mui/material/Typography';

// import { products } from 'src/_mock/products';

// import ProductCard from '../product-card';
import RequestCard from '../request-card';
import RequestSort from '../request-sort';
import RequestFilters from '../request-filters';
// ----------------------------------------------------------------------

export default function ProductsView() {
  const [requests, setRequests] = useState([]);
  const [filteredData, setFilterdData] = useState([]);
  const [openFilter, setOpenFilter] = useState(false);

  // const [order, setOrder] = useState('asc');

  // const [orderBy, setOrderBy] = useState('');

  useEffect(() => {
    setFilterdData(requests);
  }, [requests]);

  useEffect(() => {
    getRequests();
  }, []);

  const getRequests = async () => {
    await axios.get('/adm/requestsInfo').then((resp) => {
      setRequests([...resp.data]);
    });
  };

  const handleOpenFilter = () => {
    setOpenFilter(true);
  };

  const handleCloseFilter = () => {
    setOpenFilter(false);
  };

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
            filterDatas={setFilterdData}
          />

          <RequestSort />
        </Stack>
      </Stack>

      <Grid container spacing={3}>
        {filteredData.map((request) => (
          <Grid key={request.requestNo} xs={12} sm={6} md={3}>
            <RequestCard request={request} />
          </Grid>
        ))}
      </Grid>
    </Container>
  );
}
