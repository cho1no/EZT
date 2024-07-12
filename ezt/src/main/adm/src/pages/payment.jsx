import { Helmet } from 'react-helmet-async';

import { PayView } from 'src/sections/payment/view';

// ----------------------------------------------------------------------

export default function PayPage() {
  return (
    <>
      <Helmet>
        <title> 결제 관리 | EZT </title>
      </Helmet>

      <PayView />
    </>
  );
}
