import "./Navigationbar.css";

function Navbar() {
  return (
    <>
      <div id="container">
        <ul>
          <li>
            <a href="#auctions">Auctions</a>
          </li>
          <li>
            <a href="#artifacts">Artifacts</a>
          </li>
          <li>
            <a href="#events">Events</a>
          </li>
          <li>
            <a href="#about">About</a>
          </li>
        </ul>
      </div>
    </>
  );
}

export default Navbar;
