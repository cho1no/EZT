import { Helmet } from 'react-helmet-async';

import { RequestView } from 'src/sections/request/view';

// ----------------------------------------------------------------------

export default function RequestPage() {
  return (
    <>
      <Helmet>
        <title> 의뢰 관리 | EZT </title>
      </Helmet>

      <RequestView />
    </>
  );
}
