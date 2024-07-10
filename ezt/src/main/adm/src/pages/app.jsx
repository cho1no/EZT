import { Helmet } from 'react-helmet-async';

import { AppView } from 'src/sections/overview/view';

// ----------------------------------------------------------------------

export default function AppPage() {
  return (
    <>
      <Helmet>
        <title> 관리자 페이지 </title>
      </Helmet>

      <AppView />
    </>
  );
}
