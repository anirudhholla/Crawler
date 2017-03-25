
<?php
//	make	sure	browsers	see	this	page	as	utf-8	encoded	HTML
header('Content-Type:text/html;charset=utf-8');
$limit	= 10;
$query	= isset($_REQUEST['q'])	?$_REQUEST['q']:false;
$results = false;
if(isset($_GET['pagerank'])){
    if ($query)
    {
        require_once('SolrPhpClient/Apache/Solr/Service.php');
        $solr	= new Apache_Solr_Service('localhost',8983,'/solr/myexample/');

        if (get_magic_quotes_gpc() == 1)
        {
            $query	= stripslashes($query);
        }
        try
        {
            $results= $solr->search($query,0,$limit,array('sort' => 'pageRankFile desc'));
        }
        catch (Exception $e)
        {
            die("<html><head><title>SEARCH	EXCEPTION</title><body><pre>{$e->__toString()}</pre></body></html>");
        }
    }
}
else{
    if ($query)
        {
            require_once('SolrPhpClient/Apache/Solr/Service.php');
            $solr	= new Apache_Solr_Service('localhost',8983,'/solr/myexample/');

            if (get_magic_quotes_gpc() == 1)
            {
                $query	= stripslashes($query);
            }
            try
            {
                $results= $solr->search($query,	0,$limit);
            }
            catch (Exception $e)
            {
                die("<html><head><title>SEARCH	EXCEPTION</title><body><pre>{$e->__toString()}</pre></body></html>");
            }
        }
        }

?>

<html>
		<head>
				<title>PHP Solr Client Example</title>
		</head>
        
		<body>
				<form accept-charset="utf-8" method="get">
				<label for="q">Search:</label>
				<input id="q" name="q" type="text" value="<?php echo htmlspecialchars($query,ENT_QUOTES,'utf-8'); ?>"/>
				<input type="radio" name="pagerank">Page Rank</input>
				<input type="submit"/>
				</form>
<?php
//	display	results
if ($results)
{
		$total	= (int)	$results->response->numFound;
		$start	= min(1, $total);
		$end	= min($limit,	$total);
?>
	<div>Results<?php echo $start;?>-<?php echo $end;?> of <?php echo $total;?>:</div>
	<ol>
<?php
		//	iterate	result	documents
		foreach ($results->response->docs as $doc)
		{
?>
						<li>
						<?php
						$id =$doc->id;
						$id = urldecode($doc->id);
						$pos = strrpos($id,'.');
						$t1 = substr($id,$pos+1);
						$author = $doc->author;
						$dc = $doc->creation_date;
						
						if($t1=="pdf"){
						 $id = substr($id,45,-4);}
						else if($t1=="doc"){
						 $id = substr($id,45,-4);}
						else{
						$id =substr($id,45);
						}
						
						if($author==""){
						 $author = "N/A";}

						if($dc==""){
						 $dc = "N/A";}

						
				
						
						//$id = ($t1=="pdf")?substr($id,45,-4):substr($id,45);
						//$id = substr($id,45,-4);
					
						$file_size = round(intval($doc->stream_size)/1000,2);
						$title = $doc->title; 
						if($title==""){
						 $title="No Title - PDF File";}							
						?>
						<a href ="<?php echo $id;?>">link</a>
						<?php echo $title;?><br>
						<?php echo "File Size:".$file_size."KB";?><br>
						<?php echo "Author:".$author;?><br>
						<?php echo "Date Created:".$dc;?>
						
						</li>
			<?php
}
?>

				</ol>
<?php
}
?>
		</body>
</html>
