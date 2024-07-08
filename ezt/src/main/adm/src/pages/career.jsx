import { Helmet } from 'react-helmet-async';

// eslint-disable-next-line import/no-cycle
import { CareerView } from 'src/sections/career/view/';

// ----------------------------------------------------------------------

export default function CareerPage() {
  return (
    <>
      <Helmet>
        <title> Career | Minimal UI </title>
      </Helmet>

      <CareerView />
    </>
  );
}
