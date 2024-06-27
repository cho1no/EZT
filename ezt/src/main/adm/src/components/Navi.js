import React from "react";
import { Nav, NavLink, NavItem, Collapse } from "react-bootstrap";
import { Link } from "react-router-dom";

function Navi() {
  const [isOpen, setIsOpen] = React.useState({
    collapseLayouts: false,
    collapsePages: false,
    pagesCollapseAuth: false,
    pagesCollapseError: false,
  });

  const toggleCollapse = (section) => {
    setIsOpen((prevState) => ({
      ...prevState,
      [section]: !prevState[section],
    }));
  };

  return (
    <div id="layoutSidenav_nav">
      <Nav
        className="sb-sidenav accordion sb-sidenav-dark"
        id="sidenavAccordion"
      >
        <div className="sb-sidenav-menu">
          <div className="nav">
            <div className="sb-sidenav-menu-heading">Core</div>
            <NavLink as={Link} to="/" className="nav-link">
              <div className="sb-nav-link-icon">
                <i className="fas fa-tachometer-alt"></i>
              </div>
              Dashboard
            </NavLink>
            <div className="sb-sidenav-menu-heading">Interface</div>
            <NavLink
              className="nav-link collapsed"
              onClick={() => toggleCollapse("collapseLayouts")}
              aria-expanded={isOpen.collapseLayouts}
              aria-controls="collapseLayouts"
            >
              <div className="sb-nav-link-icon">
                <i className="fas fa-columns"></i>
              </div>
              Layouts
              <div className="sb-sidenav-collapse-arrow">
                <i className="fas fa-angle-down"></i>
              </div>
            </NavLink>
            <Collapse in={isOpen.collapseLayouts}>
              <nav className="sb-sidenav-menu-nested nav">
                <NavLink as={Link} to="/layout-static" className="nav-link">
                  Static Navigation
                </NavLink>
                <NavLink
                  as={Link}
                  to="/layout-sidenav-light"
                  className="nav-link"
                >
                  Light Sidenav
                </NavLink>
              </nav>
            </Collapse>
            <NavLink
              className="nav-link collapsed"
              onClick={() => toggleCollapse("collapsePages")}
              aria-expanded={isOpen.collapsePages}
              aria-controls="collapsePages"
            >
              <div className="sb-nav-link-icon">
                <i className="fas fa-book-open"></i>
              </div>
              Pages
              <div className="sb-sidenav-collapse-arrow">
                <i className="fas fa-angle-down"></i>
              </div>
            </NavLink>
            <Collapse in={isOpen.collapsePages}>
              <nav
                className="sb-sidenav-menu-nested nav accordion"
                id="sidenavAccordionPages"
              >
                <NavLink
                  className="nav-link collapsed"
                  onClick={() => toggleCollapse("pagesCollapseAuth")}
                  aria-expanded={isOpen.pagesCollapseAuth}
                  aria-controls="pagesCollapseAuth"
                >
                  Authentication
                  <div className="sb-sidenav-collapse-arrow">
                    <i className="fas fa-angle-down"></i>
                  </div>
                </NavLink>
                <Collapse in={isOpen.pagesCollapseAuth}>
                  <nav className="sb-sidenav-menu-nested nav">
                    <NavLink as={Link} to="/login" className="nav-link">
                      Login
                    </NavLink>
                    <NavLink as={Link} to="/register" className="nav-link">
                      Register
                    </NavLink>
                    <NavLink
                      as={Link}
                      to="/forgot-password"
                      className="nav-link"
                    >
                      Forgot Password
                    </NavLink>
                  </nav>
                </Collapse>
                <NavLink
                  className="nav-link collapsed"
                  onClick={() => toggleCollapse("pagesCollapseError")}
                  aria-expanded={isOpen.pagesCollapseError}
                  aria-controls="pagesCollapseError"
                >
                  Error
                  <div className="sb-sidenav-collapse-arrow">
                    <i className="fas fa-angle-down"></i>
                  </div>
                </NavLink>
                <Collapse in={isOpen.pagesCollapseError}>
                  <nav className="sb-sidenav-menu-nested nav">
                    <NavLink as={Link} to="/401" className="nav-link">
                      401 Page
                    </NavLink>
                    <NavLink as={Link} to="/404" className="nav-link">
                      404 Page
                    </NavLink>
                    <NavLink as={Link} to="/500" className="nav-link">
                      500 Page
                    </NavLink>
                  </nav>
                </Collapse>
              </nav>
            </Collapse>
            <div className="sb-sidenav-menu-heading">Addons</div>
            <NavLink as={Link} to="/charts" className="nav-link">
              <div className="sb-nav-link-icon">
                <i className="fas fa-chart-area"></i>
              </div>
              Charts
            </NavLink>
            <NavLink as={Link} to="/tables" className="nav-link">
              <div className="sb-nav-link-icon">
                <i className="fas fa-table"></i>
              </div>
              Tables
            </NavLink>
          </div>
        </div>
        <div className="sb-sidenav-footer">
          <div className="small">Logged in as:</div>
          Start Bootstrap
        </div>
      </Nav>
    </div>
  );
}

export default Navi;
