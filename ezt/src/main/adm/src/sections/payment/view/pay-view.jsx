import axios from 'axios';
import { useState, useEffect } from 'react';

import Card from '@mui/material/Card';
import Table from '@mui/material/Table';
import Container from '@mui/material/Container';
import TableBody from '@mui/material/TableBody';
import TableContainer from '@mui/material/TableContainer';
import TablePagination from '@mui/material/TablePagination';

import Scrollbar from 'src/components/scrollbar';
import Spinner from 'src/components/spinner/spinner';

import UserTableRow from '../pay-table-row';
import UserTableHead from '../pay-table-head';
import UserTableToolbar from '../pay-table-toolbar';
import TableNoData from '../../common-table/table-no-data';
import TableEmptyRows from '../../common-table/table-empty-rows';
import { emptyRows, applyFilter, getComparator } from '../../common-table/utils';

// ----------------------------------------------------------------------

export default function PayPage() {
  const [payment, setPayment] = useState([0]);

  const [page, setPage] = useState(0);

  const [order, setOrder] = useState('asc');

  const [orderBy, setOrderBy] = useState('name');

  const [filterName, setFilterName] = useState('');

  const [rowsPerPage, setRowsPerPage] = useState(5);

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    getPayment();
  }, []);

  const getPayment = async () => {
    await axios
      .get('/adm/payment')
      .then((resp) => {
        console.log(resp.data);
        setPayment(
          [...resp.data].map((_, idx) => ({
            no: resp.data.length - idx,
            ..._,
          }))
        );
        setLoading(false);
      })
      .catch(() => {});
  };

  const handleSort = (event, id) => {
    const isAsc = orderBy === id && order === 'asc';
    if (id !== '') {
      setOrder(isAsc ? 'desc' : 'asc');
      setOrderBy(id);
    }
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setPage(0);
    setRowsPerPage(parseInt(event.target.value, 10));
  };

  const handleFilterByName = (event) => {
    setPage(0);
    setFilterName(event.target.value);
  };

  const dataFiltered = applyFilter({
    inputData: payment,
    comparator: getComparator(order, orderBy),
    filterName,
    // filters: ['TITLE', 'CATEGORY_CODE_NM', 'PRICE', 'HISTORY', 'PAYMENT_TF'],
    filters: [...Object.keys(payment[0])],
  });

  const notFound = !dataFiltered.length && !!filterName;

  if (loading) {
    return <Spinner />;
  }

  return (
    <Container>
      <Card>
        <UserTableToolbar filterName={filterName} onFilterName={handleFilterByName} />

        <Scrollbar>
          <TableContainer sx={{ overflow: 'unset' }}>
            <Table sx={{ minWidth: 800 }}>
              <UserTableHead
                order={order}
                orderBy={orderBy}
                rowCount={payment.length}
                onRequestSort={handleSort}
                headLabel={[
                  { id: 'no', label: '번호' },
                  { id: 'TITLE', label: '의뢰 제목' },
                  { id: 'CATEGORY_CODE_NM', label: '구분' },
                  { id: 'PAY_DT', label: '계약일' },
                  { id: 'UNPAID_SUM', label: '잔여금액' },
                  { id: 'PRICE', label: '전체결제금액' },
                  { id: 'HISTORY', label: '진행단계' },
                  { id: 'PAYMENT_DT', label: '지급일' },
                  { id: 'PAYMENT_TF', label: '상태' },
                ]}
              />
              <TableBody>
                {dataFiltered
                  .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                  .map((row, idx) => (
                    <UserTableRow key={idx} data={row} />
                  ))}

                <TableEmptyRows
                  height={77}
                  emptyRows={emptyRows(page, rowsPerPage, payment.length)}
                />

                {notFound && <TableNoData query={filterName} colSpan={9} />}
              </TableBody>
            </Table>
          </TableContainer>
        </Scrollbar>

        <TablePagination
          page={page}
          component="div"
          count={payment.length}
          rowsPerPage={rowsPerPage}
          onPageChange={handleChangePage}
          rowsPerPageOptions={[5, 10, 25]}
          onRowsPerPageChange={handleChangeRowsPerPage}
        />
      </Card>
    </Container>
  );
}
