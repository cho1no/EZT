import { Helmet } from 'react-helmet-async';

import { ContractView } from 'src/sections/unity-contract/view/';

// ----------------------------------------------------------------------

export default function ContractPage() {
  return (
    <>
      <Helmet>
        <title> User | Minimal UI </title>
      </Helmet>

      <ContractView />
    </>
  );
}
