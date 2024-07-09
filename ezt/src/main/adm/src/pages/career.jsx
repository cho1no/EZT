import { Helmet } from 'react-helmet-async';

// eslint-disable-next-line import/no-cycle
import { CareerView } from 'src/sections/career/view/';

// ----------------------------------------------------------------------

export default function CareerPage() {
  return (
    <>
      <Helmet>
        <title> 경력 인증 관리 </title>
      </Helmet>

      <CareerView />
    </>
  );
}
